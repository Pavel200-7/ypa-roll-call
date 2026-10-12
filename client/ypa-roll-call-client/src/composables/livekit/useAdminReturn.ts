import { type Ref } from 'vue'

export interface UseAdminReturn {
  muteParticipant: (identity: string) => Promise<boolean>
  unmuteParticipant: (identity: string) => Promise<boolean>
  isAdmin: Ref<boolean>
}