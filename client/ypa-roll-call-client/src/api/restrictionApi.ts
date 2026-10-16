// api/restrictionApi.ts
import { api } from '@/api/axios'

export enum RestrictionType {
  MUTE_MICROPHONE = 'MUTE_MICROPHONE',
  MUTE_CAMERA = 'MUTE_CAMERA',
  MUTE_SCREEN_SHARE = 'MUTE_SCREEN_SHARE',
  BLOCK = 'BLOCK',
}

/**
 * Добавить ограничение участнику комнаты.
 * Требует прав администратора комнаты.
 */
export async function addRestriction(
  roomId: string,
  participantId: string,
  type: RestrictionType
): Promise<void> {
  await api.post(
    `/rooms/${roomId}/restrictions/${participantId}`,
    null,
    { params: { type } }
  )
}

/**
 * Снять ограничение с участника комнаты.
 * Требует прав администратора комнаты.
 */
export async function removeRestriction(
  roomId: string,
  participantId: string,
  type: RestrictionType
): Promise<void> {
  await api.delete(
    `/rooms/${roomId}/restrictions/${participantId}`,
    { params: { type } }
  )
}