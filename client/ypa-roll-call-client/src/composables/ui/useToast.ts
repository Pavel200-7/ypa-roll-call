import { ref, type Ref } from 'vue'

export interface UseToastReturn {
  isVisible: Ref<boolean>
  message: Ref<string>
  show: (text: string) => void
}

// Глобальное состояние — один toast на всё приложение
const isVisible = ref(false)
const message = ref('')

export function useToast(): UseToastReturn {
  function show(text: string): void {
    message.value = text
    isVisible.value = true
  }

  return { isVisible, message, show }
}