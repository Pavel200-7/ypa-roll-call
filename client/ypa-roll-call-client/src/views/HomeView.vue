<template>
  <div class="home">
    <header class="home__header">
      <button class="btn" v-if="!isAuthenticated" @click="goToLogin">Войти</button>
      <button class="btn" v-else @click="goToLogout">Выйти</button>
    </header>

    <main class="home__actions">
      <button class="btn" @click="startCall">Начать звонок</button>
      <button class="btn" @click="openJoinDialog">Присоединиться к звонку</button>
    </main>

    <JoinCallDialog
      v-model="isJoinDialogOpen"
      @confirm="onJoinConfirm"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuth } from '@/composables/useAuth'
import JoinCallDialog from '@/components/dialogs/JoinCallDialog.vue'

const router = useRouter()
const { isAuthenticated } = useAuth()

const isJoinDialogOpen = ref(false)

function goToLogin(): void {
  router.push('/login')
}

function goToLogout(): void {
  router.push('/logout')
}

function startCall(): void {
  router.push('/call/admin')
}

function openJoinDialog(): void {
  isJoinDialogOpen.value = true
}

function onJoinConfirm(roomId: string): void {
  router.push({ name: 'call-join', params: { roomId } })
}
</script>
<style scoped>
.home {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.home__header {
  display: flex;
  justify-content: flex-end;
  padding: 1rem;
}

.home__actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  padding: 2rem 1rem;
}

.home__jwt {
  margin-top: auto;
  padding: 1rem;
  word-break: break-all;
  font-size: 0.75rem;
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