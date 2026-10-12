// utils/jwt.ts

export interface LiveKitJwtPayload {
  video?: {
    room?: string
    roomJoin?: boolean
    roomAdmin?: boolean
    canPublish?: boolean
    canSubscribe?: boolean
    canPublishData?: boolean
    canPublishSources?: string[]
  }
  sub?: string
  metadata?: string
  [key: string]: unknown
}

/**
 * Декодирует payload из JWT без проверки подписи.
 * Подпись проверяет бэкенд/LiveKit — фронту достаточно прочитать claims.
 */
export function decodeJwtPayload<T = LiveKitJwtPayload>(token: string): T | null {
  try {
    console.log(token)
    const parts = token.split('.')
    if (parts.length !== 3) return null

    // Base64url → Base64 → JSON
    const base64 = parts[1]!.replace(/-/g, '+').replace(/_/g, '/')
    const padded = base64.padEnd(base64.length + ((4 - (base64.length % 4)) % 4), '=')
    const json = decodeURIComponent(
      atob(padded)
        .split('')
        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    )
    return JSON.parse(json) as T
  } catch (e) {
    console.warn('Не удалось декодировать JWT', e)
    return null
  }
}

/**
 * Извлекает roomId из LiveKit-токена.
 * Бэкенд кладёт его в grant `room` (см. AccessToken.setGrants(RoomName)).
 */
export function extractRoomIdFromToken(token: string): string | null {
  const payload = decodeJwtPayload<LiveKitJwtPayload>(token)
  return payload?.video?.room ?? null
}