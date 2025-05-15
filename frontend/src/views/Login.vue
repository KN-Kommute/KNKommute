<template>
    <div class="login">

        <div class="login__left">

            <div class="login__logo-wrapper">
                <img src="@/assets/image 1.png" alt="Anchor Logo" class="login__logo-anchor"/>
                <img src="@/assets/vector.png" alt="Car Logo" class="login__logo-car"/>
            </div>

            <h1 class="login__title"><span>KN</span>Kommute</h1>
            <p class="login__welcome">Welcome!</p>

            <form class="login__form" @submit.prevent="handleLogin">

            <div class="login__form-group">
                <input id="email" type="email" v-model="email" placeholder="Email" required/>
            </div>

            <div class="login__form-group">
                <input id="password" type="password" v-model="password" placeholder="Password" required/>
            </div>

            <div v-if="errorMessage" class="login__error">{{ errorMessage }}</div>

            <button type="submit" class="login__btn login__btn--primary">Login</button>
            <button type="button" class="login__btn login__btn--secondary" @click="goToRegister">Register</button>
    </form>
</div>

<div class="login__right">
<img src="@/assets/carrododrift.png" alt="Drifting Car" class="login__bg-image"/>
</div>
        </div>
        </template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const email = ref('')
const password = ref('')
const errorMessage = ref('')

const router = useRouter()

const login = (emailInput: string, passwordInput: string) => {
  const validUser = {
    email: 'teste@exemplo.com',
    password: '123456'
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

<style scoped lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap');

.login {
  display: flex;
  height: 100vh;
  font-family: 'Inter', sans-serif;
}

.login__left {
  width: 50%;
  padding: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.login__right {
  width: 50%;
  height: 100%;
  overflow: hidden;
}

.login__bg-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.login__logo-wrapper {
  position: relative;
  width: 149px;
  height: 132px;
  margin-bottom: 20px;
}

.login__logo-anchor {
  position: absolute;
  bottom: 132px;
  left: 50%;
  transform: translateX(-50%);
  width: 42px;
  height: 42px;
}

.login__logo-car {
  width: 149px;
  height: 132px;
  margin: 0 auto;
  display: block;
}

.login__title {
  color: #002f6c;
  font-size: 40px;
  font-weight: 400;
  text-align: center;
}

.login__title span {
  display: block;
  letter-spacing: -1px;
}

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
}

.login__form-group input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.login__btn {
  padding: 12px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  border-radius: 4px;
  width: 100%;
}

/* Login */
.login__btn--primary {
  background-color: #002f6c;
  color: white;
  border: none;
}

/* Register */
.login__btn--secondary {
  background-color: white;
  color: #333;
  border: 1px solid #ccc;
}

.login__error {
  color: red;
  font-size: 14px;
  text-align: center;
}
</style>
