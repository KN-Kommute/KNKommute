<template>

    <AuthLayout>
    <template #welcome-text>
    <p class="login__welcome">Welcome!</p>
</template>

<form class="login__form" @submit.prevent="handleLogin">
<div class="login__form-group">
<input id="email" type="email" v-model="email" placeholder="Email" required />
</div>

<div class="login__form-group">
<input id="password" type="password" v-model="password" placeholder="Password" required />
</div>

<div v-if="errorMessage" class="login__error">{{ errorMessage }}</div>

<button type="submit" class="login__btn login__btn--primary">Login</button>
<button type="button" class="login__btn login__btn--secondary" @click="goToRegister">Register</button>
        </form>
        </AuthLayout>
        </template>

<script setup lang="ts">
 import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { useAuthStore } from '../stores/auth'
import AuthLayout from '../components/AuthLayout.vue'

const email = ref('')
const password = ref('')
const errorMessage = ref('')
const router = useRouter()
const authStore = useAuthStore()

async function handleLogin() {
  errorMessage.value = ''

  try {
    const response = await axios.post('http://localhost:8912/api/auth/login', {
      email: email.value,
      password: password.value
    }, {
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json'
      }
    })

    const { user, token } = response.data

    authStore.setAuth(user, token)
    console.log(response.data) 

    alert('Login feito com sucesso!')
    router.push('/dashboard')
  } catch (error: any) {
    if (error.response?.status === 401) {
      errorMessage.value = 'Credenciais inválidas.'
    } else {
      errorMessage.value = 'Erro ao conectar. Tente novamente.'
    }
  }
}

function goToRegister() {
  router.push('/register')
}

 </script>
<style lang="scss">
.login__welcome {
  margin: 20px 0;
  font-size: 18px;
  color: #002f6c;
  font-weight: 500;
}

.login__form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  max-width: 320px;
}

.login__form-group {
  display: flex;
  flex-direction: column;

  input {
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
}

.login__btn {
  padding: 12px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  border-radius: 4px;
  width: 100%;

  &--primary {
    background-color: #002f6c;
    color: white;
    border: none;
  }

  &--secondary {
    background-color: white;
    color: #333;
    border: 1px solid #ccc;
  }
}

.login__error {
  color: red;
  font-size: 14px;
  text-align: center;
}
</style>
