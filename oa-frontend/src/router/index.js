import Vue from 'vue';
import Router from 'vue-router';
import Login from '@/components/Login.vue';
import Home from '@/components/Home.vue';

// 动态导入组件的函数
const loadView = (view) => {
  return () => import(`@/views/${view}.vue`);
};

Vue.use(Router);

// 基础路由
export const constantRoutes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    component: Login,
  },
  {
    path: '/home',
    component: Home,
  },
];

// 异步路由（根据后端返回的菜单动态生成）
export const asyncRoutes = [];

export default new Router({
  routes: constantRoutes,
});