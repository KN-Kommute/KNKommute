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
      <button type="button" class="login__btn login__btn--secondary" @click="goToRegister">
        Register
      </button>
    </form>
  </AuthLayout>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import AuthLayout from '../components/AuthLayout.vue'

const email = ref('')
const password = ref('')
const errorMessage = ref('')

const router = useRouter()

const login = (emailInput: string, passwordInput: string) => {
  const validUser = {
    email: 'teste@exemplo.com',
    password: '123456',
  }
  return emailInput === validUser.email && passwordInput === validUser.password
}

function handleLogin() {
  if (login(email.value, password.value)) {
    errorMessage.value = ''
    router.push('/dashboard')
  } else {
    errorMessage.value = 'Credenciais inválidas. Por favor, tente novamente.'
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
  transition:
    background-color 0.3s,
    color 0.3s;
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
