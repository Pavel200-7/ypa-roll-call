<template>
  <div class="call">
    <main class="call__main">
      <MainStage
        v-if="activeParticipant"
        :info="activeParticipant"
      />

      <aside class="call__sidebar">
        <ParticipantCard
          v-for="p in participantsList"
          :key="p.getIdentityString()"
          :info="p"
          :is-active="p.getIdentityString() === activeIdentity"
          @select="onSelect"
        />
      </aside>
    </main>

    <CallControls
      :is-audio-on="isMicrophoneEnabled"
      :is-video-on="isCameraEnabled"
      :is-screen-on="isScreenShareEnabled"
      @toggle-audio="media.toggleAudio"
      @toggle-video="media.toggleCamera"
      @toggle-screen="media.toggleScreenShare"
      @copy-link="onCopyLink"
      @leave="onLeave"
    />
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useConnectionTokenStore } from '@/stores/connectionToken'
import { useLiveKitConnector } from '@/composables/livekit/useLiveKitConnector'
import { useToast } from '@/composables/ui/useToast'
import type { ParticipantInfo } from '@/types/room/ParticipantInfo'
import MainStage from '@/components/call/MainStage.vue'
import ParticipantCard from '@/components/call/ParticipantCard.vue'
import CallControls from '@/components/call/CallControls.vue'

const router = useRouter()
const store = useConnectionTokenStore()
const { roomId } = storeToRefs(store)
const { show: showToast } = useToast()

const connector = useLiveKitConnector()
const { media } = connector
const {
  isMicrophoneEnabled,
  isCameraEnabled,
  isScreenShareEnabled,
} = media

const activeIdentity = ref<string | null>(null)

// Теперь отдаём сами ParticipantInfo — они реактивны через _changeTracker
const participantsList = computed<ParticipantInfo[]>(() => {
  const map = connector.participants.reactive.getAll()
  return Array.from(map.values())
})

const activeParticipant = computed<ParticipantInfo | null>(() => {
  const list = participantsList.value
  if (activeIdentity.value) {
    const found = list.find(
      (p) => p.getIdentityString() === activeIdentity.value
    )
    if (found) return found
  }
  // По умолчанию — админ
  return (
    list.find((p) => p.isAdmin()) ??
    list[0] ??
    null
  )
})

onMounted(async () => {
  const token = store.consumeToken()
  if (!token) {
    router.replace('/call/join')
    return
  }

  const url = import.meta.env.VITE_LIVEKIT_URL
  const ok = await connector.connect(url, token)
  if (!ok) {
    showToast('Не удалось подключиться к комнате')
    router.replace('/')
    return
  }

  const admin = participantsList.value.find((p) => p.isAdmin())
  if (admin) activeIdentity.value = admin.getIdentityString()
})

function onSelect(identity: string): void {
  activeIdentity.value = identity
}

async function onCopyLink(): Promise<void> {
  if (!roomId.value) return
  try {
    await navigator.clipboard.writeText(roomId.value)
    showToast('Ссылка скопирована')
  } catch {
    showToast('Не удалось скопировать')
  }
}

async function onLeave(): Promise<void> {
  await connector.disconnect()
  router.replace('/')
}
</script>

<style scoped>
.call {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #111;
}

.call__main {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.call__sidebar {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  padding: 1rem;
  width: 220px;
  background-color: #1a1a1a;
  border-left: 1px solid #333;
  overflow-y: auto;
  flex-shrink: 0;
}
</style>