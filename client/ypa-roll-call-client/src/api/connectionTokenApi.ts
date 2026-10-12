import { api } from '@/api/axios'

export interface RoomOptions {
  // пусто, но тип нужен для будущего расширения
}

export interface ConnectionTokenResponse {
  token: string
}

/**
 * Получить админский токен. Бэкенд сам генерирует roomId
 * и создаёт комнату.
 */
export async function getAdminToken(
  options: RoomOptions = {}
): Promise<ConnectionTokenResponse> {
  const { data } = await api.post<ConnectionTokenResponse>(
    '/connection-tokens/admin',
    options,
    { headers: { 'Content-Type': 'application/json' } }
  )
  return data
}

/**
 * Получить токен участника для конкретной комнаты.
 */
export async function getParticipantToken(
  roomId: string
): Promise<ConnectionTokenResponse> {
  const { data } = await api.get<ConnectionTokenResponse>(
    `/connection-tokens/participant/${roomId}`
  )
  return data
}