<template>
    <div class="PageWithSidebar">
        <Sidebar />

        <main class="PageWithSidebar__content">
            <div class="PageWithSidebar__section">
                <Logo />

                <div class="PageWithSidebar__header-row">
                    <h2 class="PageWithSidebar__title">Rides</h2>
                    <button class="PageWithSidebar__create-btn">Create</button>
                </div>

                <hr class="PageWithSidebar__divider" />

                <div class="PageWithSidebar__empty-rides">
                    <img src="@/assets/car_repair.png" alt="No Rides" class="PageWithSidebar__no-rides-image" />
                    <p class="PageWithSidebar__no-rides-text">You don’t have any rides yet.</p>
                </div>
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
    margin-top: 20px;
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
