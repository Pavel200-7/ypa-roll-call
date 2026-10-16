import { ref } from 'vue'
import * as restrictionApi from '@/api/restrictionApi'
import { RestrictionType } from '@/api/restrictionApi'

export function useRestriction() {
  const isProcessing = ref(false)
  const error = ref<string | null>(null)

  async function add(
    roomId: string,
    participantId: string,
    type: RestrictionType
  ): Promise<boolean> {
    isProcessing.value = true
    error.value = null
    try {
      await restrictionApi.addRestriction(roomId, participantId, type)
      return true
    } catch (e: any) {
      error.value = e.response?.data?.message ?? 'Не удалось добавить ограничение'
      return false
    } finally {
      isProcessing.value = false
    }
  }

  async function remove(
    roomId: string,
    participantId: string,
    type: RestrictionType
  ): Promise<boolean> {
    isProcessing.value = true
    error.value = null
    try {
      await restrictionApi.removeRestriction(roomId, participantId, type)
      return true
    } catch (e: any) {
      error.value = e.response?.data?.message ?? 'Не удалось снять ограничение'
      return false
    } finally {
      isProcessing.value = false
    }
  }

  return { 
    isProcessing, 
    error, 
    add, 
    remove 
  }
}