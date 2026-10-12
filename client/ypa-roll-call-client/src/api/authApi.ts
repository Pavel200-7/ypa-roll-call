import axios from 'axios'

const BASE_URL = import.meta.env.VITE_API_BASE_URL

export interface TokenPair {
  accessToken: string
  refreshToken: string | null
  expiresIn?: number
  tokenType?: string
}

interface RefreshTokenResponse {
  access_token: string
  refresh_token?: string
  expires_in?: number
}

interface LogoutResponse {
  // пусто, если бэкенд ничего не возвращает
}

/**
 * Возвращает URL для редиректа на бэкенд-эндпоинт логина.
 * Бэкенд сам сформирует редирект на Keycloak.
 */
export function getAuthorizeUrl(): string {
  return `${BASE_URL}/auth/authorize`
}

/**
 * Обновление access token по refresh token.
 * Использует чистый axios (без интерцепторов), чтобы не было рекурсии
 * и циклической зависимости с api/axios.ts.
 */
export async function refreshTokens(refreshToken: string): Promise<TokenPair> {
  const { data } = await axios.post<RefreshTokenResponse>(
    `${BASE_URL}/auth/refresh`,
    { refresh_token: refreshToken },
    { headers: { 'Content-Type': 'application/json' } }
  )

  return {
    accessToken: data.access_token,
    refreshToken: data.refresh_token ?? null,
    expiresIn: data.expires_in,
  }
}

/**
 * Revoke refresh token на бэкенде.
 */
export async function logout(refreshToken: string): Promise<void> {
  await axios.post<LogoutResponse>(
    `${BASE_URL}/auth/logout`,
    { refresh_token: refreshToken },
    { headers: { 'Content-Type': 'application/json' } }
  )
}