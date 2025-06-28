<!-- 헤더 레이아웃 컴포넌트 -->

<script setup>
// @ : './src' 경로 별칭 (vite.config.js 에서 설정)
// 경로 별칭 사용 이유: 상대 경로 사용 시 경로 변경 시 수정 번거로움
// 경로 별칭 사용 방법: @/config 와 같이 사용
import config from '@/config';
</script>

<template>
  <div class="jumbotron p-5 bg-primary text-white">
    <!-- config/index.js 에서 설정한 타이틀 및 서브타이틀 -->
    <h1>{{ config.title }}</h1>
    <p>{{ config.subtitle }}</p>
  </div>
</template>

<!-- 
  <style scoped> : 현재 컴포넌트에서만 적용되는 스타일
 -->
<style scoped>
.jumbotron {
  background-image: url('@/assets/images/background2.png');
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  color: white;
  padding: 2rem;
}
</style>
