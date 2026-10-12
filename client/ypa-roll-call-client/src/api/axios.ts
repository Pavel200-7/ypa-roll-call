import axios, {
  type AxiosError,
  type AxiosResponse,
  type InternalAxiosRequestConfig,
} from 'axios'
import { useAuthStore } from '@/stores/auth'
import { getAuthorizeUrl, refreshTokens } from '@/api/authApi'

interface RetryableRequest extends InternalAxiosRequestConfig {
  _retry?: boolean
}

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: { 'Content-Type': 'application/json' },
})

// --- Request interceptor: подставляем access token ---
api.interceptors.request.use(
  (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
    const store = useAuthStore()
    if (store.accessToken) {
      config.headers.set('Authorization', `Bearer ${store.accessToken}`)
    }
    return config
  }
)

// --- Response interceptor: ловим 401 и обновляем токен ---
let refreshPromise: Promise<string> | null = null

api.interceptors.response.use(
  (response: AxiosResponse): AxiosResponse => response,
  async (error: AxiosError): Promise<AxiosResponse> => {
    const store = useAuthStore()
    const original = error.config as RetryableRequest | undefined

    if (!original || error.response?.status !== 401 || original._retry) {
      return Promise.reject(error)
    }

    if (!store.refreshToken) {
      redirectToLogin()
      return Promise.reject(error)
    }

    original._retry = true

    try {
      if (!refreshPromise) {
        refreshPromise = refreshTokens(store.refreshToken)
          .then((pair) => {
            store.setTokens(pair.accessToken, pair.refreshToken)
            return pair.accessToken
          })
          .finally(() => {
            refreshPromise = null
          })
      }

      const newToken = await refreshPromise
      original.headers.set('Authorization', `Bearer ${newToken}`)
      return api(original)
    } catch (refreshError) {
      store.clearTokens()
      redirectToLogin()
      return Promise.reject(refreshError)
    }
  }
)

function redirectToLogin(): void {
  window.location.href = getAuthorizeUrl()
}

export { api }