<template>
    <aside class="Sidebar">
        <div class="Sidebar__user-header">
            <img src="@/assets/userprofile.png" alt="User Avatar" class="Sidebar__avatar" />
            <div class="Sidebar__user-text">
                <span class="Sidebar__welcome">Welcome,</span>
            </div>
        </div>

        <nav class="Sidebar__nav">
            <button
                    class="Sidebar__nav-item"
                    :class="{ 'Sidebar__nav-item--active': route.path.startsWith('/rides') }"
            @click="goToRides">

            <img src="@/assets/Vector_mini.png" alt="Car Icon" class="Sidebar__nav-icon" />
            Rides
        </button>

        <button
                class="Sidebar__nav-item"
                :class="{
          'Sidebar__nav-item--active':
            route.path.startsWith('/dashboard') || route.path.startsWith('/profile')}"
        @click="goToProfile">

        <img src="@/assets/iconbnc.png" alt="Profile Icon" class="Sidebar__nav-icon" />
        Profile
    </button>
</nav>

<button class="Sidebar__logout-btn" @click="handleLogout">Logout</button>
        </aside>
        </template>

<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

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

<style lang="scss">
.Sidebar {
  width: 350px;
  background-color: #002f6c;
  color: white;
  position: relative;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 30px 44px;
  overflow-y: hidden;

  &__user-header {
    display: flex;
    align-items: center;
    gap: 16px;
  }

  &__avatar {
    width: 70px;
    height: 70px;
    border-radius: 50%;
  }

  &__user-text {
    display: flex;
    flex-direction: column;
  }

  &__welcome {
    font-size: 16px;
    opacity: 0.9;
  }

  &__nav {
    display: flex;
    flex-direction: column;
    gap: 14px;
    margin-top: 80px;
  }

    &__nav-item {
      background: none;
      border: none;
      color: white;
      text-align: left;
      padding: 12px 14px;
      font-size: 18px;
      cursor: pointer;
      display: flex;
      align-items: center;
      gap: 12px;
      max-width: 300px;

  &--active {
    background-color: white;
    color: #002f6c;
    border-radius: 10px;

    .Sidebar__nav-icon {
      filter: brightness(0) saturate(100%) invert(12%) sepia(89%) saturate(2533%) hue-rotate(200deg) brightness(92%) contrast(105%);
    }
  }
}


  &__nav-icon {
    width: 20px;
    height: 20px;
    filter: brightness(0) invert(1);
  }

  &__logout-btn {
    background-color: #d92d20;
    color: white;
    border: none;
    padding: 10px;
    font-weight: bold;
    font-size: 14px;
    cursor: pointer;
    width: 100%;
    margin-top: auto;
    border-radius: 6px;
  }
}
</style>
