<template>
  <div class="stage">
    <video
      v-show="hasVideo"
      ref="videoEl"
      class="stage__video"
      autoplay
      playsinline
      :muted="info.isLocal()"
    />
    <div
      v-if="!hasVideo"
      class="stage__avatar"
      :style="{ backgroundColor: avatarColor }"
    >
      {{ initials }}
    </div>

    <div v-if="info.isAdmin()" class="stage__badge">Организатор</div>
    <div class="stage__name">{{ info.getIdentityString() }}</div>
  </div>
</template>

<script setup lang="ts">
import { computed, onBeforeUnmount, ref, watch, nextTick } from 'vue'
import type { Track } from 'livekit-client'
import type { ParticipantInfo } from '@/types/room/ParticipantInfo'
import { colorFromIdentity, initialsFromIdentity } from '@/utils/participant'

const props = defineProps<{
  info: ParticipantInfo
}>()

const videoEl = ref<HTMLVideoElement | null>(null)

// Реактивные вычисляемые, которые читают reactive-геттер info
const hasVideo = computed(() => props.info.reactive.hasVideo())
const videoTrack = computed<Track | null>(
  () => props.info.reactive.getVideoTrack().track
)
const avatarColor = computed(() =>
  colorFromIdentity(props.info.reactive.getIdentityString())
)
const initials = computed(() =>
  initialsFromIdentity(props.info.reactive.getIdentityString())
)

let attachedTrack: Track | null = null

watch(
  videoTrack,
  async (track) => {
    // Отвязываем старый
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
.stage {
  position: relative;
  flex: 1;
  background-color: #1a1a1a;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stage__video {
  width: 100%;
  height: 100%;
  object-fit: contain;
  display: block;
}

.stage__avatar {
  width: 240px;
  height: 240px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: Arial, sans-serif;
  font-size: 96px;
  font-weight: 600;
  color: #fff;
}

.stage__badge {
  position: absolute;
  top: 16px;
  right: 16px;
  padding: 4px 12px;
  font-family: Arial, sans-serif;
  font-size: 13px;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.6);
  border-radius: 4px;
}

.stage__name {
  position: absolute;
  bottom: 16px;
  left: 16px;
  font-family: Arial, sans-serif;
  font-size: 16px;
  color: #fff;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.9);
}
</style>