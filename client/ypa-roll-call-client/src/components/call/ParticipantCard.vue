<template>
  <div
    class="card"
    :class="{ 'card--speaking': isSpeaking, 'card--active': isActive }"
    :style="borderColorStyle"
    @click="emit('select', info.getIdentityString())"
  >
    <video
      v-show="hasVideo"
      ref="videoEl"
      class="card__video"
      autoplay
      playsinline
      muted
    />
    <div
      v-if="!hasVideo"
      class="card__avatar"
      :style="{ backgroundColor: avatarColor }"
    >
      {{ initials }}
    </div>

    <div v-if="info.isAdmin()" class="card__badge">Организатор</div>
    <div class="card__name">{{ info.getIdentityString() }}</div>
  </div>
</template>

<script setup lang="ts">
import { computed, onBeforeUnmount, ref, watch, nextTick } from 'vue'
import type { Track } from 'livekit-client'
import type { ParticipantInfo } from '@/types/room/ParticipantInfo'
import { colorFromIdentity, initialsFromIdentity } from '@/utils/participant'

const props = defineProps<{
  info: ParticipantInfo
  isActive: boolean
}>()

const emit = defineEmits<{
  (e: 'select', identity: string): void
}>()

const videoEl = ref<HTMLVideoElement | null>(null)

// Всё через reactive-геттер info
const hasVideo = computed(() => props.info.reactive.hasVideo())
const videoTrack = computed<Track | null>(
  () => props.info.reactive.getVideoTrack().track
)
const isSpeaking = computed(() => props.info.reactive.isSpeaking())
const avatarColor = computed(() =>
  colorFromIdentity(props.info.reactive.getIdentityString())
)
const initials = computed(() =>
  initialsFromIdentity(props.info.reactive.getIdentityString())
)

const borderColorStyle = computed(() => ({
  borderColor: isSpeaking.value ? avatarColor.value : 'transparent',
}))

let attachedTrack: Track | null = null

watch(
  videoTrack,
  async (track) => {
    if (attachedTrack && videoEl.value) {
      attachedTrack.detach(videoEl.value)
      attachedTrack = null
    }

    await nextTick()

    if (track && videoEl.value) {
      track.attach(videoEl.value)
      attachedTrack = track
    }
  },
  { immediate: true }
)

onBeforeUnmount(() => {
  if (attachedTrack && videoEl.value) {
    attachedTrack.detach(videoEl.value)
  }
})
</script>

<style scoped>
.card {
  position: relative;
  width: 200px;
  height: 120px;
  border-radius: 12px;
  border: 3px solid transparent;
  background-color: #2a2a2a;
  overflow: hidden;
  cursor: pointer;
  transition: border-color 0.15s ease;
  flex-shrink: 0;
}

.card--active {
  outline: 2px solid #666;
}

.card__video {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.card__avatar {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: Arial, sans-serif;
  font-size: 32px;
  font-weight: 600;
  color: #fff;
}

.card__badge {
  position: absolute;
  bottom: 6px;
  right: 6px;
  padding: 2px 8px;
  font-family: Arial, sans-serif;
  font-size: 11px;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.6);
  border-radius: 4px;
}

.card__name {
  position: absolute;
  bottom: 6px;
  left: 6px;
  max-width: 60%;
  font-family: Arial, sans-serif;
  font-size: 12px;
  color: #fff;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.8);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>