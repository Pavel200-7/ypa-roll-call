<template>
  <div class="spinner" :style="{ width: size + 'px', height: size + 'px', borderWidth: thickness + 'px' }" aria-label="Загрузка"></div>
</template>

<script setup lang="ts">
withDefaults(
  defineProps<{
    size?: number
    thickness?: number
  }>(),
  {
    size: 48,
    thickness: 4,
  }
)
</script>

<style scoped>
.spinner {
  border-style: solid;
  border-color: #e8e8e8;
  border-top-color: #909090;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>