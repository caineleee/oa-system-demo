// Vue应用程序入口文件
import Vue from 'vue';
import App from './App.vue';
import router from './router';
import axios from 'axios';

// 关闭生产环境提示
Vue.config.productionTip = false;

// 创建 axios 实例，用于HTTP请求
const axiosInstance = axios.create({
  // 使用相对路径或代理路径
  baseURL: '', 
  // 设置请求超时时间到10秒
  timeout: 10000, 
});

// 请求拦截器，用于在请求发送前进行处理
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
    // 处理请求错误
    return Promise.reject(error);
  }
);

// 响应拦截器，用于在响应返回后进行处理
axiosInstance.interceptors.response.use(
  response => {
    // 如果登录成功，保存 token
    if (response.config.url === '/login' && response.data.code === 200) {
      // 从响应数据中提取token
      const token = response.data.data.token;
      // 将token保存到本地存储
      if (token) {
        localStorage.setItem('token', token);
      }
    }
    return response;
  },
  error => {
    // 处理响应错误
    if (error.response && error.response.status === 401) {
      // token 过期或无效，清除本地存储并跳转到登录页
      localStorage.removeItem('token');
      router.push('/login');
    }
    return Promise.reject(error);
  }
);

// 将axios实例挂载到Vue原型上，方便全局使用
Vue.prototype.$http = axiosInstance;

// 创建Vue实例并挂载到DOM
new Vue({
  // 注入路由
  router,
  // 渲染根组件
  render: h => h(App),
}).$mount('#app');