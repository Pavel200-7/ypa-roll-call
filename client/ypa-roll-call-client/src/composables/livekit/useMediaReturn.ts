import { ref, type Ref, computed } from 'vue'
import {
  Room,
  RoomEvent,
  Track,
  type LocalTrackPublication,
} from 'livekit-client'
import type { ParticipantsInfo } from '@/types/room/ParticipantsInfo'

export interface UseMediaReturn {
  // Состояние
  isMicrophoneEnabled: Ref<boolean>
  isCameraEnabled: Ref<boolean>
  isScreenShareEnabled: Ref<boolean>

  attach: (room: Room) => void


  // Аудио
  startAudio: () => Promise<void>
  stopAudio: () => Promise<void>
  toggleAudio: () => Promise<void>

  // Видео (взаимоисключающее)
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
      .on(RoomEvent.LocalTrackPublished, (publication: LocalTrackPublication) => {
        const source = publication.source
        if (source === Track.Source.Microphone) {
          isMicrophoneEnabled.value = true
        } else if (source === Track.Source.Camera) {
          isCameraEnabled.value = true
        } else if (source === Track.Source.ScreenShare) {
          isScreenShareEnabled.value = true
        }
      })
      .on(RoomEvent.LocalTrackUnpublished, (publication: LocalTrackPublication) => {
        const source = publication.source
        if (source === Track.Source.Microphone) {
          isMicrophoneEnabled.value = false
        } else if (source === Track.Source.Camera) {
          isCameraEnabled.value = false
        } else if (source === Track.Source.ScreenShare) {
          isScreenShareEnabled.value = false
        }
      })
  }

  // Вызывается из useConnection после создания Room
  function attach(room: Room): void {
    bindMediaEvents(room)
  }

  // --- Аудио ---
  async function startAudio(): Promise<void> {
    if (!roomInstance.value) return
    await roomInstance.value.localParticipant.setMicrophoneEnabled(true)
  }

  async function stopAudio(): Promise<void> {
    if (!roomInstance.value) return
    await roomInstance.value.localParticipant.setMicrophoneEnabled(false)
  }

  async function toggleAudio(): Promise<void> {
    isMicrophoneEnabled.value ? await stopAudio() : await startAudio()
  }

  // --- Видео: взаимоисключающее ---
  async function startCamera(): Promise<void> {
    if (!roomInstance.value) return
    const local = roomInstance.value.localParticipant

    // Если идёт демонстрация экрана — выключаем
    if (isScreenShareEnabled.value) {
      await local.setScreenShareEnabled(false)
    }

    await local.setCameraEnabled(true)
  }

  async function stopCamera(): Promise<void> {
    if (!roomInstance.value) return
    await roomInstance.value.localParticipant.setCameraEnabled(false)
  }

  async function startScreenShare(): Promise<void> {
    if (!roomInstance.value) return
    const local = roomInstance.value.localParticipant

    // Если включена камера — выключаем
    if (isCameraEnabled.value) {
      await local.setCameraEnabled(false)
    }

    await local.setScreenShareEnabled(true)
  }

  async function stopScreenShare(): Promise<void> {
    if (!roomInstance.value) return
    await roomInstance.value.localParticipant.setScreenShareEnabled(false)
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