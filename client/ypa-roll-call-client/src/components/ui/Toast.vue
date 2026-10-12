<template>
  <Transition name="toast-fade">
    <div v-if="visible" class="toast" :class="`toast--${type}`" role="status">
      {{ message }}
    </div>
  </Transition>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

type ToastType = 'info' | 'success' | 'error'

const props = withDefaults(
  defineProps<{
    modelValue: boolean
    message: string
    type?: ToastType
    duration?: number
  }>(),
  {
    type: 'info',
    duration: 2000,
  }
)

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
}>()

const visible = ref(props.modelValue)
let timer: ReturnType<typeof setTimeout> | null = null

function hide(): void {
  visible.value = false
  emit('update:modelValue', false)
}

function scheduleHide(): void {
  if (timer) clearTimeout(timer)
  timer = setTimeout(hide, props.duration)
}

watch(
  () => props.modelValue,
  (open) => {
    visible.value = open
    if (open) scheduleHide()
  }
)

watch(
  () => props.message,
  () => {
    if (visible.value) scheduleHide()
  }
)
</script>

<style scoped>
.toast {
  position: fixed;
  left: 50%;
  bottom: 2rem;
  transform: translateX(-50%);
  padding: 0.75rem 1.5rem;
  font-family: Arial, sans-serif;
  font-size: 14px;
  color: #222;
  background-color: #ffffff;
  border: 1px solid #b0b0b0;
  border-radius: 6px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
  z-index: 2000;
  pointer-events: none;
}

.toast--success {
  background-color: #e8f5e9;
  border-color: #a5d6a7;
}

.toast--error {
  background-color: #fdecea;
  border-color: #f5b7b1;
}

.toast-fade-enter-active,
.toast-fade-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.toast-fade-enter-from,
.toast-fade-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(10px);
}
</style>