import { defineStore } from 'pinia'
import { ref, type Ref } from 'vue'

export interface AuthStore {
  accessToken: Ref<string | null>
  refreshToken: Ref<string | null>
  setTokens: (accessToken: string, refreshToken: string | null) => void
  clearTokens: () => void
}

const REFRESH_TOKEN_KEY = 'refresh_token'

export const useAuthStore = defineStore('auth', (): AuthStore => {
  const accessToken = ref<string | null>(
    sessionStorage.getItem('access_token')
  )
  const refreshToken = ref<string | null>(
    localStorage.getItem(REFRESH_TOKEN_KEY)
  )

  function setTokens(
    newAccessToken: string,
    newRefreshToken: string | null
  ): void {

    if (newRefreshToken) {
          accessToken.value = newAccessToken
          sessionStorage.setItem('access_token', newAccessToken)
    }
    if (newRefreshToken) {
      refreshToken.value = newRefreshToken
      localStorage.setItem(REFRESH_TOKEN_KEY, newRefreshToken)
    }
  }

  function clearTokens(): void {
    accessToken.value = null
    refreshToken.value = null
    localStorage.removeItem(REFRESH_TOKEN_KEY)
  }

  return { accessToken, refreshToken, setTokens, clearTokens }
})