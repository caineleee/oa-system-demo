import Vue from 'vue';
import App from './App.vue';
import router from './router';
import axios from 'axios';

Vue.config.productionTip = false;

// 创建 axios 实例
const axiosInstance = axios.create({
  baseURL: '', // 使用相对路径或代理路径
  timeout: 10000, // 增加超时时间到10秒
});

// 请求拦截器
axiosInstance.interceptors.request.use(
  config => {
    // 从本地存储获取 token 并添加到请求头
    const token = localStorage.getItem('token');
    // 登录请求不应该携带JWT token
    if (token && config.url !== '/login') {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器
axiosInstance.interceptors.response.use(
  response => {
    // 如果登录成功，保存 token
    if (response.config.url === '/login' && response.data.code === 200) {
      const token = response.data.data.token;
      if (token) {
        localStorage.setItem('token', token);
      }
    }
    return response;
  },
  error => {
    if (error.response && error.response.status === 401) {
      // token 过期或无效，清除本地存储并跳转到登录页
      localStorage.removeItem('token');
      router.push('/login');
    }
    return Promise.reject(error);
  }
);

Vue.prototype.$http = axiosInstance;

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');