<template>
  <div class="controls">
    <div class="controls__left">
      <button
        class="btn"
        :class="{ 'btn--active': isAudioOn }"
        @click="emit('toggleAudio')"
        :title="isAudioOn ? 'Выключить микрофон' : 'Включить микрофон'"
      >
        {{ isAudioOn ? '🎤' : '🔇' }}
      </button>

      <button
        class="btn"
        :class="{ 'btn--active': isVideoOn }"
        @click="emit('toggleVideo')"
        :title="isVideoOn ? 'Выключить камеру' : 'Включить камеру'"
      >
        {{ isVideoOn ? '📹' : '📷' }}
      </button>

      <button
        class="btn"
        :class="{ 'btn--active': isScreenOn }"
        @click="emit('toggleScreen')"
        :title="isScreenOn ? 'Остановить демонстрацию' : 'Показать экран'"
      >
        🖥️
      </button>

      <button class="btn" @click="emit('copyLink')" title="Скопировать ссылку">
        🔗 Скопировать ссылку
      </button>
    </div>

    <div class="controls__right">
      <button class="btn btn--danger" @click="emit('leave')">
        Отключиться
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  isAudioOn: boolean
  isVideoOn: boolean
  isScreenOn: boolean
}>()

const emit = defineEmits<{
  (e: 'toggleAudio'): void
  (e: 'toggleVideo'): void
  (e: 'toggleScreen'): void
  (e: 'copyLink'): void
  (e: 'leave'): void
}>()
</script>

<style scoped>
.controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
  padding: 0.75rem 1.5rem;
  background-color: #1a1a1a;
  border-top: 1px solid #333;
}

.controls__left,
.controls__right {
  display: flex;
  gap: 0.75rem;
}

.btn {
  font-family: Arial, sans-serif;
  font-size: 14px;
  padding: 0.6rem 1rem;
  min-height: 40px;
  min-width: 44px;
  background-color: #2a2a2a;
  border: 1px solid #444;
  border-radius: 8px;
  color: #eee;
  cursor: pointer;
  transition: background-color 0.15s ease, border-color 0.15s ease;
}

.btn:hover {
  background-color: #3a3a3a;
  border-color: #666;
}

.btn--off {
  background-color: #4a2020;
  border-color: #6a3030;
}

.btn--active {
  background-color: #204a2a;
  border-color: #306a3a;
}

.btn--danger {
  background-color: #8b1a1a;
  border-color: #b03030;
  color: #fff;
}

.btn--danger:hover {
  background-color: #a02020;
  border-color: #d04040;
}
</style>