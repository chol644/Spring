import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';

// 기능별 라우터 import
import authRoutes from './auth';
import boardRoutes from './board';
import travelRoutes from './travel';
import galleryRoutes from './gallery';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomePage },
    ...authRoutes, // 인증 관련 라우트 추가
    ...boardRoutes, // 게시판 관련 라우트 추가
    ...travelRoutes, // 여행 관련 라우트 추가
    ...galleryRoutes, // 갤러리 관련 라우트 추가
  ],
});
// 전개 연산자(...) : 배열을 펼치는 연산자
// -> import 된 모듈을 펼쳐서 현재 라우터 테이블에 추가

export default router;

/* 라우터 모듈화 장점
- 유지보수성: 기능별로 분리하여 관리 용이
- 확장성: 새로운 기능 추가 시 독립적인 라우터 파일 생성
- 가독성: 전체 라우팅 구조를 한눈에 파악 가능
*/
