import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import UserInterface from '@/views/UserInterface.vue'
import Rides from '@/views/Rides.vue'

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
    component: UserInterface // UserInterface para dashboard
  },
  {
    path: '/rides',
    name: 'Rides',
    component: Rides // Componente específico para Rides
  },

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
