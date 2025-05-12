import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/dashboard',
    name: 'UserInterface',
    component: () => import('@/views/UserInterface.vue') // lazy load
  },
  {
    path: '/rides',
    name: 'Rides',
    component: () => import('@/views/Rides.vue') // lazy load
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/UserInterface.vue') // lazy load
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
