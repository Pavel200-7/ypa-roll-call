<template>
  <div class="logout">
    <div class="logout__card">
      <h1 class="logout__title">Выход</h1>
      <Spinner />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useAuth } from '@/composables/useAuth'
import Spinner from '@/components/ui/Spinner.vue'

const { logout } = useAuth()

onMounted(() => {
  logout()
})
</script>

<style scoped>
.logout {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.logout__card {
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

.logout__title {
  margin: 0;
  font-family: Arial, sans-serif;
  font-size: 24px;
  color: #222;
}
</style>