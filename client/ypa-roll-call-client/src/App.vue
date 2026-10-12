<template>
  <router-view />
  <Toast v-model="isVisible" :message="message" type="success" />
</template>

<script setup lang="ts">
import { useToast } from '@/composables/ui/useToast'
import Toast from '@/components/ui/Toast.vue'

const { isVisible, message } = useToast()
</script>