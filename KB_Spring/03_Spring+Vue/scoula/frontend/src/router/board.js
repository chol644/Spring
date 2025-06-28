// 게시판 라우팅

export default [
  {
    path: '/board/list',
    name: 'board/list',
    component: () => import('../pages/board/BoardListPage.vue'),
  },
];
