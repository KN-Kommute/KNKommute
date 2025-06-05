<template>
  <div class="PageLayout">
    <Sidebar />

    <main class="PageLayout__content">
      <div class="PageLayout__section">
        <Logo />

        <div class="PageLayout__header-row">
          <h2 class="PageLayout__title">Profile</h2>
        </div>

        <hr class="Profile__divider" />

        <form class="__form" @submit.prevent="saveProfile">
          <input type="text" v-model="form.name" placeholder="Name" />
          <input type="email" placeholder="Email" v-model="form.email" readonly />
          <input type="text" v-model="form.phoneNumber" placeholder="Mobile Number" />

          <!-- NOVO campo para password antiga -->
          <input type="password" v-model="form.oldPassword" placeholder="Current password" />

          <input type="password" v-model="form.newPassword" placeholder="New password" />
          <input type="password" v-model="form.confirmNewPassword" placeholder="Repeat new password" />

          <button type="submit" class="__save-btn">Save profile</button>

          <p v-if="message" style="color: green;">{{ message }}</p>
          <p v-if="error" style="color: red;">{{ error }}</p>
        </form>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '../utils/axiosConfig.ts'
import Sidebar from '../components/Sidebar.vue'
import Logo from '../components/KnLogo.vue'
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()

const form = ref({
  name: '',
  email: '',
  phoneNumber: '',
  oldPassword: '',
  newPassword: '',
  confirmNewPassword: ''
})

onMounted(() => {
  const user = authStore.user
  if (user) {
    form.value.name = user.name
    form.value.email = user.email
    form.value.phoneNumber = user.phoneNumber
  }
})

const message = ref('')
const error = ref('')

async function updateName() {
  try {
    const res = await api.put('/auth/profile/update-name', {
      name: form.value.name
    })
    message.value = res.data
    authStore.user.name = form.value.name
  } catch (err: any) {
    error.value = err.response?.data || 'Erro ao atualizar o nome'
  }
}

async function updatePhoneNumber() {
  try {
    const res = await api.put('/auth/profile/update-contact', {
      phoneNumber: form.value.phoneNumber
    })
    message.value = res.data
    authStore.user.phoneNumber = form.value.phoneNumber
  } catch (err: any) {
    error.value = err.response?.data || 'Erro ao atualizar o número'
  }
}

async function updatePassword() {
  if (form.value.newPassword !== form.value.confirmNewPassword) {
    error.value = 'As passwords não coincidem.'
    return
  }

  try {
    const res = await api.put('/auth/profile/update-password', {
      oldPassword: form.value.oldPassword,
  newPassword: form.value.newPassword
    })
    message.value = res.data
    form.value.newPassword = ''
    form.value.confirmNewPassword = ''
  } catch (err: any) {
    error.value = err.response?.data || 'Erro ao atualizar a password'
  }
}



async function saveProfile() {
  message.value = ''
  error.value = ''
  await updateName()
  await updatePhoneNumber()
  if (form.value.newPassword || form.value.confirmNewPassword) {
    await updatePassword()
  }
}
</script>

<style lang="scss">
@import '@/components/style-common.scss';

.PageLayout {
  height: 100vh;
  overflow-y: hidden;

  &__title {
    font-size: 1.5rem;
    margin-bottom: 10px;
  }

  &__header-row {
    margin-bottom: 10px;
  }

  &__divider {
    margin: 0.5rem 0;
    border: none;
    border-top: 1px solid #ccc;
  }
}

.__form {
  display: flex;
  flex-direction: column;
  gap: 15px;
  font-size: 14px;

  input {
    padding: 10px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 6px;
  }
}

.__save-btn {
  background-color: #002f6c;
  color: white;
  border: none;
  padding: 10px 20px;
  font-weight: bold;
  cursor: pointer;
  align-self: flex-end;
  font-size: 14px;
  border-radius: 6px;
}
</style>
