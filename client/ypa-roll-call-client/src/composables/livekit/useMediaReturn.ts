import { ref, type Ref } from 'vue'
import {
  Room,
  RoomEvent,
  Track,
  type LocalTrack,
  type LocalTrackPublication,
  type LocalParticipant,
} from 'livekit-client'
import type { ParticipantsInfo } from '@/types/room/ParticipantsInfo'

export interface UseMediaReturn {
  isMicrophoneEnabled: Ref<boolean>
  isCameraEnabled: Ref<boolean>
  isScreenShareEnabled: Ref<boolean>

  attach: (room: Room) => void

  startAudio: () => Promise<void>
  stopAudio: () => Promise<void>
  toggleAudio: () => Promise<void>

  startCamera: () => Promise<void>
  stopCamera: () => Promise<void>
  startScreenShare: () => Promise<void>
  stopScreenShare: () => Promise<void>
  toggleCamera: () => Promise<void>
  toggleScreenShare: () => Promise<void>
}

export function useMedia(
  roomInstance: Ref<Room | null>,
  participantsInfo: ParticipantsInfo
): UseMediaReturn {
  const isMicrophoneEnabled = ref(false)
  const isCameraEnabled = ref(false)
  const isScreenShareEnabled = ref(false)

  // --- Синхронизация состояния с событиями SDK ---
  function bindMediaEvents(room: Room): void {
    room
      .on(RoomEvent.LocalTrackPublished, (
        publication: LocalTrackPublication,
        participant: LocalParticipant
      ) => {
        if (!publication.track) return

        if (publication.source === Track.Source.Microphone) {
          isMicrophoneEnabled.value = true
        } else if (publication.source === Track.Source.Camera) {
          isCameraEnabled.value = true
        } else if (publication.source === Track.Source.ScreenShare) {
          isScreenShareEnabled.value = true
        }

        const info = participantsInfo.get(participant.identity)
        if (!info) return
        info.addTrack(publication.track)
      })
      .on(RoomEvent.LocalTrackUnpublished, (
        publication: LocalTrackPublication,
        participant: LocalParticipant
      ) => {
        if (!publication.track) return

        if (publication.source === Track.Source.Microphone) {
          isMicrophoneEnabled.value = false
        } else if (publication.source === Track.Source.Camera) {
          isCameraEnabled.value = false
        } else if (publication.source === Track.Source.ScreenShare) {
          isScreenShareEnabled.value = false
        }

        const info = participantsInfo.get(participant.identity)
        if (!info) return
        info.removeTrack(publication.track.kind)
      })
  }

  function attach(room: Room): void {
    bindMediaEvents(room)
  }

  // --- Хелперы для unpublish ---
  async function unpublishBySource(source: Track.Source): Promise<void> {
    if (!roomInstance.value) return
    const local = roomInstance.value.localParticipant
    const publication = local.getTrackPublication(source)
    const track = publication?.track as LocalTrack | undefined
    if (!track) return

    track.detach()
    await local.unpublishTrack(track)
    track.stop()
  }

  // --- Аудио ---
  async function startAudio(): Promise<void> {
    if (!roomInstance.value) return
    // Если уже опубликован — не дублируем
    if (isMicrophoneEnabled.value) return
    await roomInstance.value.localParticipant.setMicrophoneEnabled(true)
  }

  async function stopAudio(): Promise<void> {
    await unpublishBySource(Track.Source.Microphone)
  }

  async function toggleAudio(): Promise<void> {
    isMicrophoneEnabled.value ? await stopAudio() : await startAudio()
  }

  // --- Камера ---
  async function startCamera(): Promise<void> {
    if (!roomInstance.value) return
    // Если идёт демонстрация экрана — удаляем её
    if (isScreenShareEnabled.value) {
      await unpublishBySource(Track.Source.ScreenShare)
    }
    if (isCameraEnabled.value) return
    await roomInstance.value.localParticipant.setCameraEnabled(true)
  }

  async function stopCamera(): Promise<void> {
    await unpublishBySource(Track.Source.Camera)
  }

  // --- Демонстрация экрана ---
  async function startScreenShare(): Promise<void> {
    if (!roomInstance.value) return
    // Если включена камера — удаляем её
    if (isCameraEnabled.value) {
      await unpublishBySource(Track.Source.Camera)
    }
    if (isScreenShareEnabled.value) return
    await roomInstance.value.localParticipant.setScreenShareEnabled(true)
  }

  async function stopScreenShare(): Promise<void> {
    await unpublishBySource(Track.Source.ScreenShare)
  }

  async function toggleCamera(): Promise<void> {
    isCameraEnabled.value ? await stopCamera() : await startCamera()
  }

  async function toggleScreenShare(): Promise<void> {
    isScreenShareEnabled.value ? await stopScreenShare() : await startScreenShare()
  }

  return {
    isMicrophoneEnabled,
    isCameraEnabled,
    isScreenShareEnabled,
    attach,
    startAudio,
    stopAudio,
    toggleAudio,
    startCamera,
    stopCamera,
    startScreenShare,
    stopScreenShare,
    toggleCamera,
    toggleScreenShare,
  }
}