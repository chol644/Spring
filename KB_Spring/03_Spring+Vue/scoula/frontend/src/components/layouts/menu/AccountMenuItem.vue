<!-- 계정 메뉴 아이템 컴포넌트 -->
<script setup>
defineProps({ username: String });
</script>

<template>
  <li class="nav-item">
    <router-link class="nav-link" to="/auth/profile">
      <i class="fa-solid fa-user"></i>
      {{ username }}
    </router-link>
  </li>
</template>
