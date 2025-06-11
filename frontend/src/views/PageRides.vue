<template>
  <div class="PageLayout">
    <Sidebar />
    <main class="PageLayout__content">
      <div class="PageLayout__section">
        <Logo />
        <div class="PageLayout__header-row">
          <h2 class="PageLayout__title">Rides</h2>
          <button class="PageLayout__create-btn" @click="showModal = true">Create</button>
          <CreateRide v-model="showModal" @create="handleCreateRide" />
        </div>
        <hr class="PageLayout__divider" />
        <component v-if="hasRides" :is="TableRide" :rides="rides" />
        <div v-else class="PageLayout__empty-rides">
          <img src="@/assets/car_repair.png" alt="No Rides" class="Rides__no-rides-image" />
          <p class="PageLayout__no-rides-text">You don’t have any rides yet.</p>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, defineAsyncComponent } from 'vue'
import api from '../utils/axiosConfig'
import { useAuthStore } from '../stores/auth'
import Sidebar from '../components/Sidebar.vue'
import Logo from '../components/KnLogo.vue'
import CreateRide from '../components/CreateRide.vue'

const TableRide = defineAsyncComponent(() => import('../components/TableRide.vue'))

const authStore = useAuthStore()
const rides = ref<any[]>([])
const showModal = ref(false)
const hasRides = computed(() => rides.value.length > 0)

async function handleCreateRide(rideData: any) {
  try {
    const response = await api.post('/rides/create', rideData, {
      headers: {
        Authorization: `Bearer ${authStore.token}`
      }
    })
    rides.value.push({
      ...response.data,
      from: response.data.origin,
      to: response.data.destination,
      date: new Date(response.data.time).toLocaleDateString('pt-PT'),
      time: new Date(response.data.time).toLocaleTimeString('pt-PT', { hour: '2-digit', minute: '2-digit' }),
      value: `${response.data.totalValue}€`,
      participating: false,
      owner: response.data.ownerName,
      phone: response.data.ownerContact,
    })
    showModal.value = false
  } catch (error) {
    console.error('Erro ao criar viagem:', error)
  }
}

async function fetchRides() {
  try {
    const response = await api.get('/rides')
    rides.value = response.data.map((r: any) => ({
      id: r.id,
      owner: r.ownerName,
      phone: r.ownerContact,
      date: new Date(r.time).toLocaleDateString('pt-PT'),
      from: r.origin,
      to: r.destination,
      time: new Date(r.time).toLocaleTimeString('pt-PT', { hour: '2-digit', minute: '2-digit' }),
      value: `${r.totalValue}€`,
      participating: false
    }))
  } catch (err) {
    console.error('Erro ao buscar rides:', err)
  }
}

onMounted(fetchRides)
</script>


<style scoped lang="scss">
@import '@/components/style-common.scss';

.PageLayout {
  height: 800px;
  overflow-y: hidden;

  &__create-btn {
    background-color: #002f6c;
    color: white;
    border: none;
    padding: 6px 12px;
    border-radius: 4px;
    font-weight: bold;
    font-size: 14px;
    cursor: pointer;
  }

  &__empty-rides {
    text-align: center;
    margin-top: 40px;
  }

  &__no-rides-image {
    width: 300px;
    margin-bottom: 20px;
  }

  &__no-rides-text {
    font-size: 14px;
    color: #002f6c;
  }

  &__title {
    font-size: 1.5rem;
  }

  &__header-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
  }

  &__divider {
    margin: 0.5rem 0;
    border: none;
    border-top: 1px solid #ccc;
  }
}
</style>
