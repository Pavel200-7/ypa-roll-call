import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import { useAuthStore } from '@/stores/auth'
import { refreshTokens } from '@/api/authApi'

const app = createApp(App)

app.use(createPinia())
app.use(router)


const store = useAuthStore()
    if (!store.accessToken && store.refreshToken) {
    try {
        const pair = await refreshTokens(store.refreshToken)
        store.setTokens(pair.accessToken, pair.refreshToken)
    } catch (e) {
        console.warn('Не удалось восстановить сессию', e)
        store.clearTokens()
    }
}

app.mount('#app')
