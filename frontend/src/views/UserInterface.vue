<template>
  <div class="ProfilePage">

    <aside :class="['ProfilePage__sidebar', { 'ProfilePage__sidebar--open': sidebarVisible }]">
      <div class="ProfilePage__user-header">
        <img src="@/assets/userprofile.png" alt="User Avatar" class="ProfilePage__avatar" />
        <div class="ProfilePage__user-text">
          <span class="ProfilePage__welcome">Welcome,</span>
        </div>
      </div>

      <nav class="ProfilePage__nav">
        <button class="ProfilePage__nav-item" :class="{ 'ProfilePage__nav-item--active': activePage === 'rides' }" @click="goToRides">Rides</button>
      <button class="ProfilePage__nav-item" :class="{ 'ProfilePage__nav-item--active': activePage === 'profile' }" @click="goToProfile">Profile</button>
  </nav>

  <button class="ProfilePage__logout-btn" @click="handleLogout">Logout</button>
        </aside>

<button class="ProfilePage__toggle-sidebar" @click="toggleSidebar">☰</button>

<main class="ProfilePage__content">
<div class="ProfilePage__profile-section">

  <div class="ProfilePage__header-icon">
    <img src="@/assets/Vector.png" alt="Car Icon" class="ProfilePage__car-icon" />
    <img src="@/assets/image 1.png" alt="Anchor Icon" class="ProfilePage__anchor-icon" />
  </div>


  <div class="ProfilePage__header-row">
    <h2 class="ProfilePage__profile-title">Profile</h2>
  </div>

  <hr class="ProfilePage__divider" />


  <form class="ProfilePage__form">
    <input type="email" placeholder="Email" />
    <input type="text" placeholder="Mobile Number" />
    <input type="password" placeholder="New password" />
    <input type="password" placeholder="Repeat new password" />
    <button type="submit" class="ProfilePage__save-btn">Save profile</button>
  </form>
</div>
</main>
        </div>
        </template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ref } from 'vue'

const router = useRouter()
const sidebarVisible = ref(false)
const activePage = ref('profile')

function handleLogout() {
  router.push('/login')
}

function closeSidebarOnMobile() {
  if (window.innerWidth <= 768) {
    sidebarVisible.value = false
  }
}

function goToProfile() {
  activePage.value = 'profile'
  router.push('/dashboard')
  closeSidebarOnMobile()
}

function goToRides() {
  activePage.value = 'rides'
  router.push('/rides')
  closeSidebarOnMobile()
}

function toggleSidebar() {
  sidebarVisible.value = !sidebarVisible.value
}
</script>

<style scoped lang="scss">
.ProfilePage {
  display: flex;
  height: 100vh;
  font-family: 'Inter', sans-serif;
  font-size: 18px;
  position: relative;
}

.ProfilePage__toggle-sidebar {
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

.ProfilePage__sidebar {
  width: 350px;
  background-color: #002f6c;
  color: white;
  position: fixed;
  top: 0;
  left: 0;
  height: 95%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 30px 24px;
  transition: transform 0.3s ease-in-out;
  transform: translateX(-100%);
  z-index: 999;
  overflow-y: auto;
}

.ProfilePage__sidebar--open {
  transform: translateX(0);
}

@media (min-width: 768px) {
  .ProfilePage__sidebar {
    transform: none !important;
    position: relative;
  }

  .ProfilePage__toggle-sidebar {
    display: none;
  }

  .ProfilePage__content {
    margin-left: 350px;
  }
}

.ProfilePage__user-header {
  display: flex;
  align-items: center;
  gap: 16px;
}

.ProfilePage__avatar {
  width: 70px;
  height: 70px;
  border-radius: 50%;
}

.ProfilePage__user-text {
  display: flex;
  flex-direction: column;
}

.ProfilePage__welcome {
  font-size: 16px;
  opacity: 0.9;
}

.ProfilePage__nav {
  display: flex;
  flex-direction: column;
  gap: 14px;
  margin-top: 40px;
}

.ProfilePage__nav-item {
  background: none;
  border: none;
  color: white;
  text-align: left;
  padding: 12px 14px;
  font-size: 18px;
  cursor: pointer;
}

.ProfilePage__nav-item--active {
  background-color: white;
  color: #002f6c;
  border-radius: 10px;
}

.ProfilePage__logout-btn {
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

.ProfilePage__content {
  flex: 1;
  padding: 40px 60px;
  transition: margin-left 0.3s ease-in-out;
  margin-left: 0;
}

.ProfilePage__profile-section {
  max-width: 800px;
  margin: 0 auto;
  padding-top: 30px;
}

.ProfilePage__header-icon {
  position: relative;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.ProfilePage__car-icon {
  width: 100px;
}

.ProfilePage__anchor-icon {
  position: absolute;
  top: -30px;
  width: 30px;
}

.ProfilePage__header-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.ProfilePage__profile-title {
  color: #002f6c;
  font-size: 24px;
  font-weight: 600;
  margin: 0;
}

.ProfilePage__divider {
  border: none;
  border-top: 1px solid #002f6c;
  margin-bottom: 32px;
}

.ProfilePage__form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  font-size: 16px;
}

.ProfilePage__form input {
  padding: 14px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

.ProfilePage__save-btn {
  background-color: #002f6c;
  color: white;
  border: none;
  padding: 14px 24px;
  font-weight: bold;
  cursor: pointer;
  align-self: flex-end;
  font-size: 16px;
  border-radius: 6px;
}


@media (max-width: 768px) {
  .ProfilePage__toggle-sidebar {
    display: block;
  }

  .ProfilePage__content {
    margin-left: 0;
  }

  .ProfilePage__sidebar {
    width: 80%;
  }

  .ProfilePage__header-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .ProfilePage__save-btn {
    align-self: flex-start;
  }
}
</style>
