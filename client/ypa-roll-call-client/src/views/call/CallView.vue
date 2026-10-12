<template>
  <div class="call">
    <header class="call__header">
      <h1 class="call__title">Страница звонка</h1>
    </header>
    <main class="call__body">
      <template v-if="token">
        <p class="call__hint">Здесь будет видеокомната LiveKit</p>
        <p class="call__debug">Token: {{ token.slice(0, 20) }}…</p>
      </template>
      <template v-else>
        <p class="call__hint">Токен не получен</p>
        <Spinner />
      </template>
    </main>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useConnectionTokenStore } from '@/stores/connectionToken'
import Spinner from '@/components/ui/Spinner.vue'

const router = useRouter()
const store = useConnectionTokenStore()
const token = ref<string | null>(null)

onMounted(() => {
  token.value = store.consumeToken()

  if (!token.value) {
    // Токена нет — перезагрузка страницы или прямой заход.
    // Отправляем на страницу входа в комнату, где roomId возьмётся из стора.
    router.replace('/call/join')
  }
})
</script>

<style scoped>
.call {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  font-family: Arial, sans-serif;
}

.call__header {
  padding: 1rem 2rem;
  border-bottom: 1px solid #b0b0b0;
}

.call__title {
  margin: 0;
  font-size: 20px;
  color: #222;
}

.call__body {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 1rem;
}

.call__hint {
  font-size: 16px;
  color: #555;
}

.call__debug {
  font-size: 12px;
  color: #888;
  word-break: break-all;
}
</style>