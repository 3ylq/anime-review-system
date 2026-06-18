<template>
  <div class="main-layout">
    <nav-bar />
    <div class="content-wrapper">
      <RouterView />
    </div>
  </div>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue'
</script>

<style scoped>
.main-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.content-wrapper {
  flex: 1;
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
  width: 100%;
}
</style>