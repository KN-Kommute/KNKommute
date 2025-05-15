<template>
    <div class="RidesPage">
        <aside :class="['RidesPage__sidebar', { 'RidesPage__sidebar--open': sidebarVisible }]">
            <div class="RidesPage__user-header">

                <img src="@/assets/userprofile.png" alt="User Avatar" class="RidesPage__avatar" />
                <div class="RidesPage__user-text">
                    <span class="RidesPage__welcome">Welcome,</span>
                </div>
            </div>

            <nav class="RidesPage__nav">
                <button
                        class="RidesPage__nav-item"
                        :class="{ 'RidesPage__nav-item--active': activePage === 'rides' }"
                @click="goToRides"
                >
                Rides
            </button>
            <button
                    class="RidesPage__nav-item"
                    :class="{ 'RidesPage__nav-item--active': activePage === 'profile' }"
            @click="goToProfile"
            >
            Profile
        </button>
    </nav>

    <button class="RidesPage__logout-btn" @click="handleLogout">Logout</button>
        </aside>

<button class="RidesPage__toggle-sidebar" @click="toggleSidebar">☰</button>

<main class="RidesPage__content">
<div class="RidesPage__rides-section">
    <div class="RidesPage__header-icon">
        <img src="@/assets/Vector.png" alt="Car Icon" class="RidesPage__top-car-icon" />
        <img src="@/assets/image 1.png" alt="Anchor Icon" class="RidesPage__anchor-icon" />
    </div>

    <div class="RidesPage__header-row">
        <h2 class="RidesPage__rides-title">Rides</h2>
        <button class="RidesPage__create-btn">Create</button>
    </div>

    <hr class="RidesPage__divider" />

    <div class="RidesPage__empty-rides">
        <img src="@/assets/car_repair.png" alt="No Rides" class="RidesPage__no-rides-image" />
        <p class="RidesPage__no-rides-text">You haven’t created any rides yet.</p>
    </div>
</div>
</main>
        </div>
        </template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ref } from 'vue'


const router = useRouter()

const sidebarVisible = ref(false)
const activePage = ref('rides')

function handleLogout() {
  router.push('/login')
}

function goToProfile() {
  activePage.value = 'profile'
  router.push('/dashboard')
}

function goToRides() {
  activePage.value = 'rides'
  router.push('/rides')
}

function toggleSidebar() {
  sidebarVisible.value = !sidebarVisible.value
}
</script>

<style scoped lang="scss">

.RidesPage {
  display: flex;
  height: 100vh;
  font-family: 'Inter', sans-serif;
  font-size: 18px;
  position: relative;
}


.RidesPage__toggle-sidebar {
  display: none;
  position: absolute;
  top: 20px;
  left: 20px;
  background-color: #002f6c;
  color: white;
  border: none;
  padding: 10px;
  font-size: 20px;
  cursor: pointer;
  z-index: 1000;
}


.RidesPage__sidebar {
  width: 350px;
  background-color: #002f6c;
  color: white;
  position: fixed;
  top: 0;
  left: 0;
  height: 95%; /* kept as requested */
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 30px 24px;
  transition: transform 0.3s ease-in-out;
  transform: translateX(-100%);
  z-index: 999;
  overflow-y: auto;
}


.RidesPage__sidebar--open {
  transform: translateX(0);
}


@media (min-width: 768px) {
  .RidesPage__sidebar {
    transform: none !important;
    position: relative;
  }

  .RidesPage__toggle-sidebar {
    display: none;
  }

  .RidesPage__content {
    margin-left: 300px;
  }
}


.RidesPage__user-header {
  display: flex;
  align-items: center;
  gap: 16px;
}


.RidesPage__avatar {
  width: 70px;
  height: 70px;
  border-radius: 50%;
}


.RidesPage__user-text {
  display: flex;
  flex-direction: column;
}


.RidesPage__welcome {
  font-size: 16px;
  opacity: 0.9;
}


.RidesPage__nav {
  display: flex;
  flex-direction: column;
  gap: 14px;
  margin-top: 40px;
}


.RidesPage__nav-item {
  background: none;
  border: none;
  color: white;
  text-align: left;
  padding: 12px 14px;
  font-size: 18px;
  cursor: pointer;
}


.RidesPage__nav-item--active {
  background-color: white;
  color: #002f6c;
  border-radius: 10px;
}


.RidesPage__logout-btn {
  background-color: #d92d20;
  color: white;
  border: none;
  padding: 12px;
  font-weight: bold;
  font-size: 16px;
  cursor: pointer;
  width: 100%;
  margin-top: auto; /* push logout to the bottom */
}


.RidesPage__content {
  flex: 1;
  padding: 40px 60px;
  transition: margin-left 0.3s ease-in-out;
  margin-left: 0;
}


.RidesPage__rides-section {
  max-width: 800px;
  margin: 0 auto;
  padding-top: 30px;
}


.RidesPage__header-icon {
  position: relative;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}


.RidesPage__top-car-icon {
  width: 100px;
}


.RidesPage__anchor-icon {
  position: absolute;
  top: -30px;
  width: 30px;
}


.RidesPage__header-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}


.RidesPage__rides-title {
  color: #002f6c;
  font-size: 24px;
  font-weight: 600;
  margin: 0;
}


.RidesPage__divider {
  border: none;
  border-top: 1px solid #002f6c;
  margin-bottom: 32px;
}


.RidesPage__empty-rides {
  text-align: center;
  margin-top: 40px;
}


.RidesPage__no-rides-image {
  width: 400px;
  margin-bottom: 40px;
}


.RidesPage__no-rides-text {
  font-size: 18px;
  color: #002f6c;
}


.RidesPage__create-btn {
  background-color: #002f6c;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  font-weight: bold;
  font-size: 16px;
  cursor: pointer;
}


@media (max-width: 768px) {
  /* Show sidebar toggle button */
  .RidesPage__toggle-sidebar {
    display: block;
  }


  .RidesPage__content {
    margin-left: 0;
    padding: 20px 24px;
  }

  .RidesPage__sidebar {
    width: 80%;
  }


  .RidesPage__sidebar--open {
    transform: translateX(0);
  }


  .RidesPage__header-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }


  .RidesPage__create-btn {
    align-self: flex-start;
  }


  .RidesPage__no-rides-image {
    width: 100%;
    max-width: 300px;
  }
}
</style>
