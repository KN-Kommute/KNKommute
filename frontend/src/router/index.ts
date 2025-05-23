import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import UserInterface from '@/views/UserInterface.vue'
import Rides from '@/views/Rides.vue'

const routes = [
  {
    path: '/',
    redirect: '/login' // Redireciona a rota raiz para o login
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
    component: UserInterface
  },
  {
    path: '/rides',
    name: 'Rides',
    component: Rides
  },
  {
    path: '/profile',
    name: 'Profile',
    component: UserInterface // Atenção: isso reaproveita UserInterface
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
