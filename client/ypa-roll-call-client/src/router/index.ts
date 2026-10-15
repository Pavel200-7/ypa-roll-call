import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/auth/LoginView.vue'),
    },
    {
      path: '/callback',
      name: 'callback',
      component: () => import('@/views/auth/CallbackViev.vue'),
    },
    {
      path: '/logout',
      name: 'logout',
      component: () => import('@/views/auth/LogoutView.vue'),
    },
    {
      path: '/call',
      name: 'call',
      component: () => import('@/views/call/CallView.vue'),
    },
    {
      path: '/call/admin',
      name: 'call-admin',
      component: () => import('@/views/call/CreateRoomEntryView.vue'),
    },
    {
      path: '/call/kicked',
      name: 'call-kicked',
      component: () => import('@/views/call/KickedFromCallView.vue'),
    },
    {
      path: '/call/join/:roomId?',
      name: 'call-join',
      component: () => import('@/views/call/JoinRoomEntryView.vue'),
    },
  ],
})

export default router
