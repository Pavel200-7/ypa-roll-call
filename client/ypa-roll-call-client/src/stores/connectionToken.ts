import { defineStore } from 'pinia'
import { ref } from 'vue'

const ROOM_ID_KEY = 'active_room_id'

export const useConnectionTokenStore = defineStore('connectionToken', () => {
  const token = ref<string | null>(null)
  const roomId = ref<string | null>(sessionStorage.getItem(ROOM_ID_KEY))

  function setToken(newToken: string): void {
    token.value = newToken
  }

  function clearToken(): void {
    token.value = null
  }

  function consumeToken(): string | null {
    const current = token.value
    token.value = null
    return current
  }

  function setRoomId(newRoomId: string): void {
    roomId.value = newRoomId
    sessionStorage.setItem(ROOM_ID_KEY, newRoomId)
  }

  function clearRoomId(): void {
    roomId.value = null
    sessionStorage.removeItem(ROOM_ID_KEY)
  }

  return {
    token,
    roomId,
    setToken,
    clearToken,
    consumeToken,
    setRoomId,
    clearRoomId,
  }
})