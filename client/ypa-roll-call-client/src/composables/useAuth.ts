import { computed, ref, type ComputedRef, type Ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import * as authApi from '@/api/authApi'

export interface UseAuthReturn {
  isAuthenticated: ComputedRef<boolean>
  isProcessing: Ref<boolean>
  error: Ref<string | null>
  accessToken: ComputedRef<string | null>
  login: () => void
  handleCallback: () => boolean
  logout: () => Promise<void>
}

const ACCESS_TOKEN_KEY = 'access_token'
const REFRESH_TOKEN_KEY = 'refresh_token'

export function useAuth(): UseAuthReturn {
  const store = useAuthStore()
  const router = useRouter()

  const isProcessing = ref(false)
  const error = ref<string | null>(null)

  const isAuthenticated = computed<boolean>(() => !!store.accessToken)
  const accessToken = computed<string | null>(() => store.accessToken)

  /**
   * Редирект на бэкенд-эндпоинт логина.
   * Бэкенд сам сформирует редирект на Keycloak (client_id, state, PKCE).
   */
  function login(): void {
    window.location.href = authApi.getAuthorizeUrl()
  }

  /**
   * Обработка возврата от бэкенда после успешного логина.
   * Токены приходят во фрагменте URL: /callback#access_token=...&refresh_token=...
   */
  function handleCallback(): boolean {
    const params = new URLSearchParams(window.location.hash.slice(1))
    const accessTokenValue = params.get(ACCESS_TOKEN_KEY)
    const refreshTokenValue = params.get(REFRESH_TOKEN_KEY)

    if (!accessTokenValue) {
      error.value = 'Токены не получены'
      router.replace('/login')
      return false
    }

    store.setTokens(accessTokenValue, refreshTokenValue)
    router.replace({ path: '/', hash: '' })
    return true
  }

  /**
   * Выход: revoke refresh token на бэкенде + очистка стора + редирект.
   */
  async function logout(): Promise<void> {
    isProcessing.value = true
    try {
      if (store.refreshToken) {
        await authApi.logout(store.refreshToken)
      }
    } catch (e) {
      console.warn('Logout request failed', e)
    } finally {
      store.clearTokens()
      isProcessing.value = false
      await router.push('/login')
    }
  }

  return {
    isAuthenticated,
    isProcessing,
    error,
    accessToken,
    login,
    handleCallback,
    logout,
  }
}