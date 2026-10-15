<!-- views/call/KickedFromCallView.vue -->
<template>
  <div class="entry">
    <div class="entry__card">
      <h1 class="entry__title">Соединение прервано</h1>
      <p class="entry__subtitle">
        Кто-то подключился к этому звонку из другой вкладки или устройства
        с тем же аккаунтом. Текущая сессия была завершена.
      </p>
      <p class="entry__countdown">
        Возврат на главную через {{ secondsLeft }}…
      </p>
      <button class="btn" @click="goHome">На главную сейчас</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const secondsLeft = ref(5)
let timer: ReturnType<typeof setInterval> | null = null

onMounted(() => {
  timer = setInterval(() => {
    secondsLeft.value -= 1
    if (secondsLeft.value <= 0) {
      goHome()
    }
  }, 1000)
})

onBeforeUnmount(() => {
  if (timer) clearInterval(timer)
})

function goHome(): void {
  if (timer) clearInterval(timer)
  router.replace('/')
}
</script>

<style scoped>
.entry {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.entry__card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.25rem;
  padding: 2rem 3rem;
  max-width: 480px;
  background-color: #ffffff;
  border: 1px solid #b0b0b0;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  text-align: center;
}

.entry__title {
  margin: 0;
  font-family: Arial, sans-serif;
  font-size: 22px;
  color: #c0392b;
}

.entry__subtitle {
  margin: 0;
  font-family: Arial, sans-serif;
  font-size: 15px;
  color: #555;
  line-height: 1.5;
}

.entry__countdown {
  margin: 0;
  font-family: Arial, sans-serif;
  font-size: 14px;
  color: #888;
}

.btn {
  font-family: Arial, sans-serif;
  font-size: 16px;
  padding: 0.75rem 2rem;
  min-width: 200px;
  min-height: 48px;
  background-color: #e8e8e8;
  border: 1px solid #b0b0b0;
  border-radius: 6px;
  color: #222;
  cursor: pointer;
  transition: background-color 0.15s ease, border-color 0.15s ease;
}

.btn:hover {
  background-color: #d0d0d0;
  border-color: #909090;
}

.btn:active {
  background-color: #c0c0c0;
}
</style>