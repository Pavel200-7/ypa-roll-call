import { shallowRef, ref, type Ref } from 'vue'
import {
  Room,
  RoomEvent,
  type RemoteParticipant,
  type RemoteTrack,
  type RemoteTrackPublication,
  type Participant,
  type LocalTrackPublication,
  type TrackPublication,
} from 'livekit-client'
import type { ParticipantsInfo } from '@/types/room/ParticipantsInfo'

export interface UseConnectionReturn {
  roomInstance: Ref<Room | null>
  localParticipantIdentity: Ref<string | null>
  isConnected: Ref<boolean>
  isConnecting: Ref<boolean>
  connect: (token: string) => Promise<Room | null>
  disconnect: () => void
}

export function useConnection(participantsInfo: ParticipantsInfo): UseConnectionReturn {
  const url: string = import.meta.env.VITE_API_SFU_URL
  const roomInstance = shallowRef<Room | null>(null)   // ← shallowRef
  const localParticipantIdentity = ref<string | null>(null)
  const isConnected = ref(false)
  const isConnecting = ref(false)

  function bindEvents(room: Room): void {
    room
      .on(RoomEvent.ParticipantConnected, (participant: RemoteParticipant) => {
        let metadata = room.localParticipant?.metadata ?? null
        participantsInfo.addNewParticipant(participant.identity, false, metadata)
      })
      .on(RoomEvent.ParticipantDisconnected, (participant: RemoteParticipant) => {
        participantsInfo.deleteParticipant(participant.identity)
      })
      .on(RoomEvent.TrackSubscribed, (
        track: RemoteTrack,
        _publication: RemoteTrackPublication,
        participant: RemoteParticipant
      ) => {
        const existing = participantsInfo.get(participant.identity)
        if (!existing) return
        existing.addTrack(track)
      })
      .on(RoomEvent.TrackUnsubscribed, (
        track: RemoteTrack,
        _publication: RemoteTrackPublication,
        participant: RemoteParticipant
      ) => {
        const existing = participantsInfo.get(participant.identity)
        if (!existing) return
        existing.removeTrack(track.kind)
      })
      .on(RoomEvent.ActiveSpeakersChanged, (speakers: Participant[]) => {
        participantsInfo.resetSpeakers()
        for (const speaker of speakers) {
          const info = participantsInfo.get(speaker.identity)
          if (info) info.setIsSpeaking(true)
        }
      })
      .on(RoomEvent.Disconnected, () => {
        isConnected.value = false
        participantsInfo.clear()
      })
  }

  function addExistingParticipants(room: Room): void {
    for (const [identity, participant] of room.remoteParticipants) {
      if (identity === localParticipantIdentity.value) continue

      let videoTrack: RemoteTrack | null = null
      let audioTrack: RemoteTrack | null = null

      for (const [, publication] of participant.videoTrackPublications) {
        if (publication.track) {
          videoTrack = publication.track as RemoteTrack
          break
        }
      }

      for (const [, publication] of participant.audioTrackPublications) {
        if (publication.track) {
          audioTrack = publication.track as RemoteTrack
          break
        }
      }

      let metadata = room.localParticipant?.metadata ?? null
      participantsInfo.addExistingParticipant(identity, videoTrack, audioTrack, false, metadata)
    }
  }

  async function connect(token: string): Promise<Room | null> {
    if (isConnected.value) return roomInstance.value

    isConnecting.value = true

    try {
      const room = new Room({
        adaptiveStream: true,
        dynacast: true,
      })

      roomInstance.value = room
      bindEvents(room)

      await room.connect(url, token)

      localParticipantIdentity.value = room.localParticipant?.identity ?? null
      let metadata = room.localParticipant?.metadata ?? null
      
      if (localParticipantIdentity.value) {
        participantsInfo.addNewParticipant(localParticipantIdentity.value, true, metadata)
      }

      addExistingParticipants(room)
      isConnected.value = true
      return room
    } catch (err) {
      console.error('Ошибка подключения к комнате:', err)
      roomInstance.value = null
      return null
    } finally {
      isConnecting.value = false
    }
  }

  function disconnect(): void {
    if (!roomInstance.value) return
    localParticipantIdentity.value = null
    roomInstance.value.disconnect()
    roomInstance.value = null
    isConnected.value = false
    participantsInfo.clear()
  }

  return {
    roomInstance,
    localParticipantIdentity,
    isConnected,
    isConnecting,
    connect,
    disconnect,
  }
}