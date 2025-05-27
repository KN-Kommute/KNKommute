<template>
  <router-view />
</template>

<script setup>
</script>

<style>

html, body {
  margin: 0;
  padding: 0;
  font-family: 'Inter', sans-serif;
  overflow-x: hidden !important;
  width: 100% !important;
  box-sizing: border-box;
}


*, *::before, *::after {
  box-sizing: inherit;
}


.el-dialog__wrapper {
  overflow-x: hidden !important;
}

.el-dialog {
  max-width: 100%;
}


</style>