<template>
  <div class="login">
    <div class="login__card">
      <h1 class="login__title">Вход</h1>
      <Spinner />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useAuth } from '@/composables/useAuth'
import Spinner from '@/components/ui/Spinner.vue'

const { login } = useAuth()

// Опционально: автоматический редирект на Keycloak при заходе на /login
onMounted(() => {
  login()
})
</script>

<style scoped>
.login {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.login__card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  padding: 2rem 3rem;
  background-color: #ffffff;
  border: 1px solid #b0b0b0;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.login__title {
  margin: 0;
  font-family: Arial, sans-serif;
  font-size: 24px;
  color: #222;
}

.login__subtitle {
  margin: 0;
  font-family: Arial, sans-serif;
  font-size: 16px;
  color: #555;
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