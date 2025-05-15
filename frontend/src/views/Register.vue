<template>
    <div class="register">
        <div class="register__left">
            <div class="register__logo-wrapper">
                <img src="@/assets/image 1.png" alt="Anchor Logo" class="register__anchor-logo" />
                <img src="@/assets/Vector.png" alt="Car Logo" class="register__car-logo" />
            </div>

            <h1 class="register__brand-title"><span>KN</span>Kommute</h1>
            <p class="register__welcome-text">Insert your data below</p>

            <form class="register__form" @submit.prevent="handleRegister">
            <div class="register__form-group">
                <input
                        :class="{ 'register__input--error': nameError }"
                        type="text"
                        placeholder="Name"
                        v-model="name"
                        required
                        class="register__input"
                />
                <span v-if="nameError" class="register__error-msg">{{ nameError }}</span>
            </div>

            <input
                    type="email"
                    placeholder="Email"
                    v-model="email"
                    required
                    class="register__input"
            />

            <input
                    type="password"
                    placeholder="Password"
                    v-model="password"
                    required
                    class="register__input"
            />

            <div class="register__form-group">
                <input
                        :class="{ 'register__input--error': passwordError }"
                        type="password"
                        placeholder="Repeat Password"
                        v-model="repeatPassword"
                        required
                        class="register__input"
                />
                <span v-if="passwordError" class="register__error-msg">{{ passwordError }}</span>
            </div>

            <div class="register__buttons">
                <button type="submit" class="btn btn--primary">Create account</button>
                <button type="button" class="btn btn--secondary" @click="goBack">Go back</button>
        </div>
    </form>
</div>

<div class="register__right">
<img src="@/assets/carrododrift.png" alt="Drifting Car" class="register__bg-image" />
</div>
        </div>
        </template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const email = ref('')
const password = ref('')
const repeatPassword = ref('')
const name = ref('')

const nameError = ref('')
const passwordError = ref('')

function handleRegister() {
  nameError.value = ''
  passwordError.value = ''

  const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]+$/
  const trimmedName = name.value.trim()
  const nameParts = trimmedName.split(/\s+/).filter(part => part.length >= 2)

  if (nameParts.length < 2) {
    nameError.value = 'Please enter your full name (at least two words with 2+ letters).'
  } else if (!nameRegex.test(trimmedName)) {
    nameError.value = 'Name can only contain letters and spaces.'
  }

  if (password.value !== repeatPassword.value) {
    passwordError.value = 'Passwords do not match.'
  }

  if (!nameError.value && !passwordError.value) {
    alert('Account created successfully!')
    router.push('/login')
  }
}

function goBack() {
  router.push('/login')
}
</script>

<style scoped lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap');

.register {
  display: flex;
  height: 100vh;
  font-family: 'Inter', sans-serif;
  overflow: hidden;
}

.register__left,
.register__right {
  width: 50%;
  height: 100%;
}

.register__left {
  padding: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.register__right {
  overflow: hidden;
}

.register__bg-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.register__logo-wrapper {
  position: relative;
  width: 149px;
  height: 132px;
  margin-bottom: 20px;
}

.register__anchor-logo {
  position: absolute;
  bottom: 132px;
  left: 50%;
  transform: translateX(-50%);
  width: 42px;
  height: 42px;
}

.register__car-logo {
  width: 149px;
  height: 132px;
  display: block;
  margin: 0 auto;
}

.register__brand-title {
  color: #002f6c;
  font-size: 40px;
  font-weight: 400;
  text-align: center;
}

.register__brand-title span {
  display: block;
  letter-spacing: -1px;
}

.register__welcome-text {
  margin: 20px 0;
  font-size: 18px;
  color: #002f6c;
  font-weight: 500;
}

.register__form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  max-width: 320px;
}

.register__form-group {
  display: flex;
  flex-direction: column;
}

.register__input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
}

.register__input--error {
  border-color: red;
}

.register__error-msg {
  color: red;
  font-size: 12px;
  margin-top: 4px;
  display: block;
}

.register__buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 100%;
}

.btn {
  padding: 12px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s, color 0.3s;
  width: 107%;
  text-align: center;
}

.btn--primary {
  background-color: #002f6c;
  color: white;
  border: none;
}

.btn--secondary {
  background-color: white;
  color: #333;
  border: 1px solid #ccc;
}
</style>
