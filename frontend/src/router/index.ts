import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/PageLogin.vue'
import Register from '../views/PageRegister.vue'
import UserInterface from '../views/UserInterface.vue'
import Rides from '../views/PageRides.vue'
import ElementPlus from 'element-plus'

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
    component: UserInterface
  },
  {
    path: '/rides',
    name: 'Rides',
    component: Rides
  },

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
