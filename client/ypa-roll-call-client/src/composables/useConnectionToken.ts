import { ref, type Ref } from 'vue'
import * as connectionTokenApi from '@/api/connectionTokenApi'
import type { RoomOptions } from '@/api/connectionTokenApi'
import { useConnectionTokenStore } from '@/stores/connectionToken'

export interface UseConnectionTokenReturn {
  token: Ref<string | null>
  isLoading: Ref<boolean>
  error: Ref<string | null>
  fetchAdminToken: (options?: RoomOptions) => Promise<string | null>
  fetchParticipantToken: (roomId: string) => Promise<string | null>
}

export function useConnectionToken(): UseConnectionTokenReturn {
  const store = useConnectionTokenStore()
  const token = ref<string | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  async function fetchAdminToken(
    options: RoomOptions = {}
  ): Promise<string | null> {
    isLoading.value = true
    error.value = null
    try {
      const response = await connectionTokenApi.getAdminToken(options)
      token.value = response.token
      store.setToken(response.token)
      return response.token
    } catch (e) {
      error.value = 'Не удалось получить токен организатора'
      console.error(e)
      return null
    } finally {
      isLoading.value = false
    }
  }

  async function fetchParticipantToken(
    roomId: string
  ): Promise<string | null> {
    isLoading.value = true
    error.value = null
    try {
      const response = await connectionTokenApi.getParticipantToken(roomId)
      token.value = response.token
      store.setToken(response.token)
      store.setRoomId(roomId)
      return response.token
    } catch (e) {
      error.value = 'Не удалось получить токен участника'
      console.error(e)
      return null
    } finally {
      isLoading.value = false
    }
  }

  return {
    token,
    isLoading,
    error,
    fetchAdminToken,
    fetchParticipantToken,
  }
}