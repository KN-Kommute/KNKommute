<template>
<div class="PageWithSidebar">
<Sidebar />

    <main class="PageWithSidebar__content">
<div class="PageWithSidebar__section">
<Logo />

        <div class="PageWithSidebar__header-row">
<h2 class="PageWithSidebar__title">Rides</h2>
<button class="PageWithSidebar__create-btn" @click="showModal = true">Create</button>
<CreateRide v-model="showModal" @create="handleCreateRide" />
</div>

        <hr class="PageWithSidebar__divider" />

        <component
          v-if="hasRides"
          :is="TableRide"
        />

        <div v-else class="PageWithSidebar__empty-rides">
<img
            src="@/assets/car_repair.png"
            alt="No Rides"
            class="PageWithSidebar__no-rides-image"
          />
<p class="PageWithSidebar__no-rides-text">You don’t have any rides yet.</p>
</div>
</div>
</main>
</div>
</template>

<script setup lang="ts">
import { ref, computed, defineAsyncComponent } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import Logo from '@/components/Logo.vue'
import CreateRide from '@/components/CreateRide.vue' // IMPORTAÇÃO AQUI

const rides = ref([
  // Simule dados reais se quiser
 { }
])

const hasRides = computed(() => rides.value.length > 0)
const TableRide = defineAsyncComponent(() => import('@/components/TableRide.vue'))

// Controle do modal
const showModal = ref(false)

// Handler da criação
function handleCreateRide(ride: any) {
  rides.value.push(ride)
  console.log('Nova carona criada:', ride)
}
</script>


<style scoped lang="scss">
@import '@/components/style-common.scss';

.PageWithSidebar {
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
