<template>
    <div class="auth-layout">
        <div class="auth-layout__left">
            <div class="auth-layout__logo-wrapper">
                <img src="@/assets/image 1.png" alt="Anchor Logo" class="auth-layout__logo-anchor" />
                <img src="@/assets/vector.png" alt="Car Logo" class="auth-layout__logo-car" />
            </div>
            <h1 class="auth-layout__title"><span>KN</span>Kommute</h1>
            <p class="auth-layout__subtitle">
                <slot name="subtitle" />
            </p>
            <div class="auth-layout__form-wrapper">
                <slot />
            </div>
        </div>
        <div class="auth-layout__right">
            <img src="@/assets/carrododrift.png" alt="Drifting Car" class="auth-layout__bg-image" />
        </div>
    </div>
</template>


<script setup lang="ts">
// caso seja preciso no futuro
</script>


<style lang="scss">
.auth-layout {
  display: flex;
  height: 100vh;
  font-family: 'Inter', sans-serif;

  &__left {
    width: 45%;
    padding: 30px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  &__right {
    width: 55%;
    height: 100%;
    overflow: hidden;
  }

  &__bg-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  &__logo-wrapper {
    position: relative;
    width: 120px;
    height: 106px;
    margin-bottom: 16px;
  }

  &__logo-anchor {
    position: absolute;
    bottom: 105px;
    left: 50%;
    transform: translateX(-50%);
    width: 32px;
    height: 32px;
  }

  &__logo-car {
    width: 120px;
    height: 106px;
    margin: 0 auto;
    display: block;
  }

  &__title {
    color: #002f6c;
    font-size: 32px;
    font-weight: 400;
    text-align: center;

    span {
      display: block;
      letter-spacing: -1px;
    }
  }

  &__subtitle {
    margin: 16px 0;
    font-size: 16px;
    color: #002f6c;
    font-weight: 500;
    text-align: center;
  }

  &__form-wrapper {
    width: 100%;
    max-width: 280px;
    margin-top: 12px;
  }
}
</style>
