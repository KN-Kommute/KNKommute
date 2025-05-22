<template>
  <div class="PageWithSidebar">
    <Sidebar />

    <main class="PageWithSidebar__content">
      <div class="PageWithSidebar__section">
        <Logo />

        <div class="PageWithSidebar__header-row">
          <h2 class="PageWithSidebar__title">Profile</h2>
        </div>

        <hr class="PageWithSidebar__divider" />

        <form class="__form">
          <input type="email" placeholder="Email" />
          <input type="text" placeholder="Mobile Number" />
          <input type="password" placeholder="New password" />
          <input type="password" placeholder="Repeat new password" />
          <button type="submit" class="__save-btn">Save profile</button>
        </form>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import Sidebar from '@/components/Sidebar.vue'
import Logo from '@/components/Logo.vue'
</script>

<style lang="scss">
@import '@/components/style-common.scss';

.PageWithSidebar {
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
