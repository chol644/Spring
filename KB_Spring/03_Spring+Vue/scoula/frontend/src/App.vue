<script setup>
import { RouterView } from 'vue-router';
import DefaultLayout from './components/layouts/DefaultLayout.vue';
</script>

<template>
  <DefaultLayout>
    <!-- 
      라우터 뷰 컴포넌트
      - 라우팅 테이블에 정의된 경로에 따라 표시되는 컴포넌트를 나타냄
    -->
    <RouterView />
  </DefaultLayout>
</template>

<!-- Composition API -->
<style scoped></style>
