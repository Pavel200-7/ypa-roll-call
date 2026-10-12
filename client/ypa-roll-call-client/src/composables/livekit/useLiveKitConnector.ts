import { ref, shallowRef, type Ref, type ShallowRef } from 'vue'
import { Room } from 'livekit-client'
import { ParticipantsInfo } from '@/types/room/ParticipantsInfo'
import { useConnection, type UseConnectionReturn } from '@/composables/livekit/useConnectionReturn'
import { useMedia, type UseMediaReturn } from '@/composables/livekit/useMediaReturn'

export interface UseLiveKitConnectorReturn {
  // Состояние комнаты
  room: ShallowRef<Room | null>
  participants: ParticipantsInfo
  isConnected: Ref<boolean>
  isConnecting: Ref<boolean>
  error: Ref<string | null>

  // Основные действия
  connect: (url: string, token: string) => Promise<boolean>
  disconnect: () => Promise<void>

  // Дочерние композаблы
  connection: UseConnectionReturn
  media: UseMediaReturn
}

export function useLiveKitConnector(): UseLiveKitConnectorReturn {
  const participants = new ParticipantsInfo()

  const connection = useConnection(participants)
  const media = useMedia(connection.roomInstance, participants)

  const room = connection.roomInstance
  const isConnected = connection.isConnected
  const isConnecting = connection.isConnecting
  const error = ref<string | null>(null)

  async function connect(url: string, token: string): Promise<boolean> {
    error.value = null
    try {
      const created = await connection.connect(token)
      if (!created) {
        error.value = 'Не удалось подключиться к комнате'
        return false
      }
      media.attach(created)
      return true
    } catch (e) {
      error.value = e instanceof Error ? e.message : 'Неизвестная ошибка подключения'
      return false
    }
  }

  async function disconnect(): Promise<void> {
    connection.disconnect()
  }

  return {
    room,
    participants,
    isConnected,
    isConnecting,
    error,
    connect,
    disconnect,
    connection,
    media,
  }
}