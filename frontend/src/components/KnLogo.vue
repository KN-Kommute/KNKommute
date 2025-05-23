<template>
    <div class="logo">
        <img src="@/assets/image 1.png" alt="Anchor" class="logo__anchor" />
        <img src="@/assets/Vector.png" alt="Car" class="logo__car" />
    </div>
</template>


<style lang="scss">
.logo {
  position: relative;
  width: fit-content;
  margin: 10px auto;

  &__anchor {
    position: absolute;
    top: -28px;
    left: 50%;
    transform: translateX(-50%);
    width: 30px;
    height: auto;
  }

  &__car {
    display: block;
    width: 100px;
    height: auto;
  }
}
</style>
