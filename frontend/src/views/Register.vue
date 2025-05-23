<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const email = ref('')
const password = ref('')
const repeatPassword = ref('')
const name = ref('')
const phoneNumber = ref('')

const nameError = ref('')
const passwordError = ref('')
const phoneNumberError = ref('')
const apiError = ref('') // Para mostrar erros da API
const apiSuccess = ref('') // Para mostrar sucesso

// Validação simples do número de telefone com regex
watch(phoneNumber, (newVal) => {
  const regex = /^\+?[0-9\s\-]{7,15}$/
  if (newVal && !regex.test(newVal)) {
    phoneNumberError.value = 'Please enter a valid phone number.'
  } else {
    phoneNumberError.value = ''
  }
})

async function handleRegister() {
  nameError.value = ''
  passwordError.value = ''
  phoneNumberError.value = ''
  apiError.value = ''
  apiSuccess.value = ''

  const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]+$/
  const trimmedName = name.value.trim()
  const nameParts = trimmedName.split(/\s+/).filter(part => part.length >= 2)

  if (nameParts.length < 2) {
    nameError.value = 'Please enter your full name (at least two words with 2+ letters).'
    return
  } else if (!nameRegex.test(trimmedName)) {
    nameError.value = 'Name can only contain letters and spaces.'
    return
  }

  if (password.value !== repeatPassword.value) {
    passwordError.value = 'Passwords do not match.'
    return
  }

  if (phoneNumberError.value) {
    // Se há erro no telefone, impede submissão
    return
  }

  try {
    const response = await fetch('http://localhost:8912/api/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        name: name.value,
        email: email.value,
        password: password.value,
        confirmPassword: repeatPassword.value,
        phoneNumber: phoneNumber.value.trim()
      })
    })

    if (!response.ok) {
      const errorData = await response.json()
      console.log('Erro da API:', errorData)  // para debug
      apiError.value = errorData.message || 'Registration failed.'
      return
    }


    apiSuccess.value = 'Account created successfully!'
    setTimeout(() => router.push('/login'), 1500)
  } catch (err) {
    apiError.value = 'Error connecting to server.'
  }
}

function goBack() {
  router.back()
}
</script>

<template>
  <div class="register-page">
    <div class="left-side">
      <div class="logo-wrapper">
        <img src="@/assets/image 1.png" alt="Anchor Logo" class="anchor-logo" />
        <img src="@/assets/Vector.png" alt="Car Logo" class="car-logo" />
      </div>

      <h1 class="brand-title"><span>KN</span>Kommute</h1>
      <p class="welcome-text">Insert your data below</p>

      <form class="register-form" @submit.prevent="handleRegister">
        <div>
          <input :class="{ 'input-error': nameError }" type="text" placeholder="Name" v-model="name" required />
          <span v-if="nameError" class="error-msg">{{ nameError }}</span>
        </div>

        <input type="email" placeholder="Email" v-model="email" required />

        <input type="password" placeholder="Password" v-model="password" required />

        <div>
          <input :class="{ 'input-error': passwordError }" type="password" placeholder="Repeat Password"
            v-model="repeatPassword" required />
          <span v-if="passwordError" class="error-msg">{{ passwordError }}</span>
        </div>

        <div>
          <input :class="{ 'input-error': phoneNumberError }" type="tel" placeholder="Phone Number" v-model="phoneNumber"
            pattern="^\+?[0-9\s\-]{7,15}$" title="Please enter a valid phone number" />
          <span v-if="phoneNumberError" class="error-msg">{{ phoneNumberError }}</span>
        </div>

        <button class="create-btn" type="submit">Create account</button>
        <button class="back-btn" type="button" @click="goBack">Go back</button>

        <span v-if="apiError" class="error-msg">{{ apiError }}</span>
        <span v-if="apiSuccess" class="success-msg">{{ apiSuccess }}</span>
      </form>
    </div>

    <div class="right-side">
      <img src="@/assets/carrododrift.png" alt="Drifting Car" class="bg-image" />
    </div>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap');

.register-page {
  display: flex;
  height: 100vh;
  font-family: 'Inter', sans-serif;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

.input-error {
  border-color: red;
}

.error-msg {
  color: red;
  font-size: 12px;
  margin-top: 4px;
  display: block;
}

.success-msg {
  color: green;
  margin-top: 16px;
  font-weight: bold;
  text-align: center;
}

.left-side,
.right-side {
  width: 50%;
  height: 100%;
}

.left-side {
  padding: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.right-side {
  overflow: hidden;
}

.bg-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.logo-wrapper {
  position: relative;
  width: 149px;
  height: 132px;
  margin-bottom: 20px;
}

.anchor-logo {
  position: absolute;
  bottom: 132px;
  left: 50%;
  transform: translateX(-50%);
  width: 42px;
  height: 42px;
}

.car-logo {
  width: 149px;
  height: 132px;
  display: block;
  margin: 0 auto;
}

.brand-title {
  color: #002f6c;
  font-size: 40px;
  font-weight: 400;
  text-align: center;
}

.brand-title span {
  display: block;
  letter-spacing: -1px;
}

.welcome-text {
  margin: 20px 0;
  font-size: 18px;
  color: #002f6c;
  font-weight: 500;
}

.register-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 70%;
  max-width: 300px;
}

.register-form input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
}

.input-error {
  border-color: red;
}

.error-msg {
  color: red;
  font-size: 12px;
  margin-top: 4px;
  display: block;
}

.create-btn,
.back-btn {
  padding: 12px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 2px;
}

.create-btn {
  background-color: #002f6c;
  color: white;
  border: none;
}

.back-btn {
  background-color: white;
  color: #333;
  border: 1px solid #ccc;
}
</style>
