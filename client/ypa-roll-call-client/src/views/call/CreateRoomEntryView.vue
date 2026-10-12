<template>
  <div class="entry">
    <div class="entry__card">
      <h1 class="entry__title">Создание звонка</h1>
      <p class="entry__subtitle">{{ message }}</p>

      <Spinner v-if="isLoading" />
      <button v-else-if="hasError" class="btn" @click="goHome">На главную</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useConnectionToken } from '@/composables/useConnectionToken'
import { useConnectionTokenStore } from '@/stores/connectionToken'
import { extractRoomIdFromToken } from '@/utils/jwt'
import Spinner from '@/components/ui/Spinner.vue'

const router = useRouter()
const store = useConnectionTokenStore()
const { fetchAdminToken, isLoading, error } = useConnectionToken()
const message = ref('Создаём новую комнату...')
const hasError = ref(false)

onMounted(async () => {
  const token = await fetchAdminToken()
  if (!token) {
    message.value = error.value ?? 'Не удалось создать комнату'
    hasError.value = true
    return
  }

  // Извлекаем roomId из JWT и сохраняем в стор
  const roomId = extractRoomIdFromToken(token)
  if (!roomId) {
    console.warn('roomId не найден в токене')
  } else {
    store.setRoomId(roomId)
  }

  router.replace('/call')
})

function goHome(): void {
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
  gap: 1.5rem;
  padding: 2rem 3rem;
  background-color: #ffffff;
  border: 1px solid #b0b0b0;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.entry__title {
  margin: 0;
  font-family: Arial, sans-serif;
  font-size: 24px;
  color: #222;
}

.entry__subtitle {
  margin: 0;
  font-family: Arial, sans-serif;
  font-size: 16px;
  color: #555;
  text-align: center;
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