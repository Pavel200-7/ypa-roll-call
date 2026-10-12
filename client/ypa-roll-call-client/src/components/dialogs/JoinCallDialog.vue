<template>
  <div v-if="modelValue" class="dialog-overlay" @click.self="close">
    <div class="dialog" role="dialog" aria-modal="true" aria-labelledby="join-dialog-title">
      <h2 id="join-dialog-title" class="dialog__title">Присоединиться к звонку</h2>

      <input
        ref="inputRef"
        v-model="input"
        class="dialog__input"
        :class="{ 'dialog__input--error': hasError }"
        type="text"
        placeholder="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
        maxlength="36"
        autocomplete="off"
        spellcheck="false"
        @input="onInput"
        @keydown.enter="onConfirm"
      />

      <p v-if="hasError" class="dialog__error">
        Неверный формат UUID
      </p>

      <div class="dialog__actions">
        <button class="btn" @click="onPaste">Вставить</button>
        <button class="btn btn--primary" :disabled="!isValid" @click="onConfirm">
          Подтвердить
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, nextTick, ref, watch } from 'vue'
import { useToast } from '@/composables/ui/useToast'

const props = defineProps<{
  modelValue: boolean
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'confirm', roomId: string): void
}>()

const UUID_REGEX = /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i

const { show: showToast } = useToast()

const input = ref('')
const touched = ref(false)
const inputRef = ref<HTMLInputElement | null>(null)

const isValid = computed(() => UUID_REGEX.test(input.value))
const hasError = computed(() => touched.value && input.value.length > 0 && !isValid.value)

function onInput(event: Event): void {
  const target = event.target as HTMLInputElement
  const digits = target.value.replace(/[^0-9a-fA-F]/g, '').slice(0, 32)

  const parts = [
    digits.slice(0, 8),
    digits.slice(8, 12),
    digits.slice(12, 16),
    digits.slice(16, 20),
    digits.slice(20, 32),
  ].filter(Boolean)

  const formatted = parts.join('-')
  input.value = formatted
  target.value = formatted
}

async function onPaste(): Promise<void> {
  try {
    const text = await navigator.clipboard.readText()
    const trimmed = text.trim()

    if (UUID_REGEX.test(trimmed)) {
      input.value = trimmed.toLowerCase()
      touched.value = true
      showToast('Вставлено из буфера обмена')
      return
    }

    // Попробуем «вычистить» и отформатировать то, что в буфере
    const digits = trimmed.replace(/[^0-9a-fA-F]/g, '').slice(0, 32)
    if (digits.length === 32) {
      const parts = [
        digits.slice(0, 8),
        digits.slice(8, 12),
        digits.slice(12, 16),
        digits.slice(16, 20),
        digits.slice(20, 32),
      ]
      input.value = parts.join('-').toLowerCase()
      touched.value = true
      showToast('Вставлено из буфера обмена')
    } else {
      touched.value = true
      showToast('В буфере нет корректного UUID')
    }
  } catch (e) {
    console.warn('Не удалось прочитать буфер обмена', e)
    touched.value = true
    showToast('Не удалось прочитать буфер обмена')
  }
}

function onConfirm(): void {
  touched.value = true
  if (!isValid.value) return
  emit('confirm', input.value.toLowerCase())
  close()
}

function close(): void {
  emit('update:modelValue', false)
}

watch(
  () => props.modelValue,
  async (open) => {
    if (open) {
      input.value = ''
      touched.value = false
      await nextTick()
      inputRef.value?.focus()
    }
  }
)
</script>

<style scoped>
.dialog-overlay {
  position: fixed;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 1000;
}

.dialog {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  max-width: 480px;
  padding: 1.5rem 2rem;
  background-color: #ffffff;
  border: 1px solid #b0b0b0;
  border-radius: 8px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.dialog__title {
  margin: 0;
  font-family: Arial, sans-serif;
  font-size: 20px;
  color: #222;
}

.dialog__input {
  width: 100%;
  box-sizing: border-box;
  padding: 0.75rem 1rem;
  font-family: 'Courier New', monospace;
  font-size: 16px;
  color: #222;
  background-color: #fafafa;
  border: 1px solid #b0b0b0;
  border-radius: 6px;
  outline: none;
  transition: border-color 0.15s ease, background-color 0.15s ease;
}

.dialog__input:focus {
  border-color: #909090;
  background-color: #ffffff;
}

.dialog__input--error {
  border-color: #c0392b;
}

.dialog__error {
  margin: -0.5rem 0 0;
  font-family: Arial, sans-serif;
  font-size: 13px;
  color: #c0392b;
}

.dialog__actions {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-top: 0.5rem;
}

.btn {
  font-family: Arial, sans-serif;
  font-size: 16px;
  padding: 0.5rem 1.5rem;
  min-height: 40px;
  background-color: #e8e8e8;
  border: 1px solid #b0b0b0;
  border-radius: 6px;
  color: #222;
  cursor: pointer;
  transition: background-color 0.15s ease, border-color 0.15s ease;
}

.btn:hover:not(:disabled) {
  background-color: #d0d0d0;
  border-color: #909090;
}

.btn:active:not(:disabled) {
  background-color: #c0c0c0;
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn--primary {
  background-color: #d4e4ff;
  border-color: #7ba3d4;
}

.btn--primary:hover:not(:disabled) {
  background-color: #bcd5ff;
  border-color: #5a8ac4;
}
</style>