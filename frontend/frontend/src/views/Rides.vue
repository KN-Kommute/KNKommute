<template>
<div class="dashboard">
<!-- Sidebar -->
<aside class="sidebar">
<div class="user-header">
<img src="@/assets/userprofile.png" alt="User Avatar" class="avatar" />
<div class="user-text">
<span class="welcome">Welcome,</span>
</div>
</div>

            <nav class="nav">
<button class="nav-item active" @click="goToRides">Rides</button>
<button class="nav-item" @click="goToProfile">Profile</button>
</nav>

    <button class="logout-btn" @click="handleLogout">Logout</button>
</aside>

        <!-- Main Content -->
<main class="content">
<div class="profile-section">
<!-- Car + Anchor -->
<div class="header-icon">
<img src="@/assets/Vector.png" alt="Car Icon" class="top-car-icon" />
<img src="@/assets/image 1.png" alt="Anchor Icon" class="anchor-icon" />
</div>

    <!-- Title + Create button -->
<div class="header-row">
<h2 class="profile-title">Rides</h2>

<el-button class="create-btn" type="primary" @click="showModal = true">Create</el-button>
<CreateRideModal v-model="showModal" @create="handleRideCreate" />


</div>

    <hr class="divider" />
  <div class="p-4">
   <TableRides :rides="rides" />
  </div>

</div>
</main>
</div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import TableRides from './TableRides.vue'
import CreateRideModal from './CreateRideModal.vue'
import { ref } from 'vue'

const showModal = ref(false)

// Lista de caronas
const rides = ref<any[]>([])

// Lógica ao criar nova carona
function handleRideCreate(ride: any) {
  rides.value.push(ride)
  console.log('Nova viagem criada:', ride)
}

const router = useRouter()

function handleLogout() {
  router.push('/login')
}

function goToProfile() {
  router.push('/dashboard')
}

function goToRides() {
  router.push('/rides')
}

</script>

<style scoped>
.dashboard {
  display: flex;
  height: 100vh;
  font-family: 'Inter', sans-serif;
  font-size: 18px;
}

/* Sidebar */
.sidebar {
  width: 400px;
  background-color: #002f6c;
  color: white;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 30px 24px;
}

.user-header {
  display: flex;
  align-items: center;
  gap: 16px;
}

.avatar {
  width: 70px;
  height: 70px;
  border-radius: 50%;
}

.user-text {
  display: flex;
  flex-direction: column;
}

.welcome {
  font-size: 16px;
  opacity: 0.9;
}

.username {
  font-weight: 600;
  font-size: 18px;
}

.nav {
  display: flex;
  flex-direction: column;
  gap: 14px;
  margin-top: 40px;
}

.nav-item {
  background: none;
  border: none;
  color: white;
  text-align: left;
  padding: 12px 14px;
  font-size: 18px;
  cursor: pointer;
}

.nav-item.active {
  background-color: white;
  color: #002f6c;
  border-radius: 10px;
}

.logout-btn {
  background-color: #d92d20;
  color: white;
  border: none;
  padding: 12px;
  font-weight: bold;
  font-size: 16px;
  cursor: pointer;
  width: 100%;
  margin-top: auto;
}

/* Main Content */
.content {
  flex: 1;
  padding: 40px 60px;
}

.profile-section {
max-width: 100vh;
  margin: 0 auto;
  padding-top: 30px;
}

.header-icon {
  position: relative;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  margin-top: -20px;
}

.top-car-icon {
  width: 100px;
}

.anchor-icon {
  position: absolute;
  top: -30px;
  width: 30px;
}

.header-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.profile-title {
  color: #002f6c;
  font-size: 24px;
  font-weight: 600;
  margin: 0;
}

.create-btn {
  background-color: #002f6c;
  color: white;
  border: none;
  padding: 8px 16px;
  font-size: 14px;
  border-radius: 4px;
  cursor: pointer;
}

.divider {
  border: none;
  border-top: 1px solid #002f6c;
  margin-bottom: 32px;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.empty-img {
  max-width: 200px;
  margin-bottom: 20px;
}

.empty-state p {
  color: #002f6c;
  font-size: 16px;
  line-height: 1.4;
}
</style>