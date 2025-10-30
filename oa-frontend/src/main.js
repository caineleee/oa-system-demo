import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

// 设置 axios 的默认基础 URL
axios.defaults.baseURL = ''

// 添加请求拦截器
axios.interceptors.request.use(
  config => {
    // 从本地存储中获取 token
    const token = localStorage.getItem('token')
    // 如果 token 存在，则添加到请求头中
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    // 处理请求错误
    return Promise.reject(error)
  }
)

// 添加响应拦截器
axios.interceptors.response.use(
  response => {
    // 对响应数据做点什么
    // 检查响应中是否包含token，并保存到localStorage
    if (response.data && response.data.data && response.data.data.token) {
      localStorage.setItem('token', response.data.data.token);
    }
    return response
  },
  error => {
    // 对响应错误做点什么
    if (error.response && error.response.status === 401) {
      // 如果是 401 错误，清除本地 token 并跳转到登录页
      localStorage.removeItem('token')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

// 将 axios 添加到 Vue 的原型链上，以便在组件中使用 this.$http 调用
Vue.prototype.$http = axios

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')