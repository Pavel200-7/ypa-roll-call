<template>
  <div>Завершаем вход...</div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useAuth } from '@/composables/useAuth'

const { handleCallback } = useAuth()

onMounted(() => {
  handleCallback()
})
</script>