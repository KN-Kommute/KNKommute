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
          <input type="password" v-model="form.newPassword" placeholder="New password" />
          <input
            type="password"
            v-model="form.confirmNewPassword"
            placeholder="Repeat new password"
          />
          <button type="submit" class="__save-btn">Save profile</button>
        </form>

        <!-- TABELA DE PEDIDOS -->
        <table class="Profile__table">
          <thead>
            <tr>
              <th>From</th>
              <th>To</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Lisbon</td>
              <td>Porto</td>
              <td>
                <button class="Profile__btn Profile__btn--accept">✓ Accept</button>
                <button class="Profile__btn Profile__btn--reject">✕ Reject</button>
              </td>
            </tr>
            <tr>
              <td>Braga</td>
              <td>Coimbra</td>
              <td>
                <button class="Profile__btn Profile__btn--accept">✓ Accept</button>
                <button class="Profile__btn Profile__btn--reject">✕ Reject</button>
              </td>
            </tr>
          </tbody>
        </table>
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
  confirmNewPassword: '',
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
    const res = await axios.put('https://localhost:8912/api/auth/profile/update-name', {
      name: form.value.name,
    })
    message.value = res.data
    authStore.user.name = form.value.name
  } catch (err: any) {
    error.value = err.response?.data || 'Erro ao atualizar o nome'
  }
}

async function updatePhoneNumber() {
  try {
    const res = await axios.put('https://localhost:8912/api/auth/profile/update-contact', {
      phoneNumber: form.value.phoneNumber,
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
    const res = await axios.put('https://localhost:8912/api/auth/profile/update-password', {
      oldPassword: '',
      newPassword: form.value.newPassword,
    })
    message.value = res.data
    form.value.newPassword = ''
    form.value.confirmNewPassword = ''
  } catch (err: any) {
    error.value = err.response?.data || 'Erro ao atualizar a password'
  }
}

function saveProfile() {
  error.value = ''
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

// NOVO ESTILO MINIMALISTA
.Profile__table {
  width: 100%;
  margin-top: 2rem;
  border-collapse: separate;
  border-spacing: 0 10px;
  font-size: 14px;

  th {
    text-align: left;
    padding: 12px 16px;
    color: #444;
    font-weight: 600;
    background-color: transparent;
    border-bottom: 1px solid #ddd;
  }

  td {
    padding: 12px 16px;
    background: #fff;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.04);
    border-radius: 8px;
    vertical-align: middle;
  }

  tr {
    transition: transform 0.2s ease;
  }

  tr:hover {
    transform: scale(1.01);
  }

  td:first-child,
  td:last-child {
    border-top-left-radius: 8px;
    border-bottom-left-radius: 8px;
  }

  td:last-child {
    border-top-right-radius: 8px;
    border-bottom-right-radius: 8px;
  }
}

.Profile__btn {
  padding: 8px 14px;
  border: none;
  border-radius: 6px;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease;
  margin-right: 6px;

  &--accept {
    background-color: #e0f7ec;
    color: #1e7f5c;

    &:hover {
      background-color: #c8f0df;
    }
  }

  &--reject {
    background-color: #fdecea;
    color: #c0392b;

    &:hover {
      background-color: #fbd5d1;
    }
  }
}
</style>
