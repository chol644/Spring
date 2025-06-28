// 여행 라우팅

export default [
  {
    path: '/travel/list',
    name: 'travel/list',
    component: () => import('../pages/travel/TravelListPage.vue'),
  },
];
