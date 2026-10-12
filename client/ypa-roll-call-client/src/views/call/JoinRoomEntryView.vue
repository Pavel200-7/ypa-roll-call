<template>
  <div class="entry">
    <div class="entry__card">
      <h1 class="entry__title">Присоединение к звонку</h1>
      <p class="entry__subtitle">{{ message }}</p>

      <Spinner v-if="isLoading" />
      <button v-else-if="hasError" class="btn" @click="goHome">На главную</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useConnectionToken } from '@/composables/useConnectionToken'
import { useConnectionTokenStore } from '@/stores/connectionToken'
import Spinner from '@/components/ui/Spinner.vue'

const route = useRoute()
const router = useRouter()
const store = useConnectionTokenStore()
const { fetchParticipantToken, isLoading, error } = useConnectionToken()
const message = ref('Получаем токен участника...')
const hasError = ref(false)

onMounted(async () => {
  const routeRoomId = route.params.roomId
  const roomId =
    (typeof routeRoomId === 'string' && routeRoomId) || store.roomId

  if (!roomId) {
    message.value = 'Не указан идентификатор комнаты'
    hasError.value = true
    return
  }

  // roomId из URL (или из стора) — сохраняем в стор ДО запроса токена,
  // чтобы при ошибке он тоже остался
  store.setRoomId(roomId)

  const token = await fetchParticipantToken(roomId)
  console.log(token)
  if (!token) {
    message.value = error.value ?? 'Не удалось получить токен'
    hasError.value = true
    return
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
}
</style>