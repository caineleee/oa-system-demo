<template>
  <div class="login-container">
    <h2>OA System Login</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <div class="form-group">
        <label for="captcha">Captcha:</label>
        <img 
          :src="captchaUrl" 
          alt="Captcha Image" 
          @click="refreshCaptcha"
          class="captcha-image"
        />
        <input type="text" id="captcha" v-model="captcha" required />
      </div>
      <div class="form-group remember-me-group">
        <input type="checkbox" id="rememberMe" v-model="rememberMe" />
        <label for="rememberMe">Remember Me</label>
      </div>
      <button type="submit">Login</button>
    </form>
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      captcha: '',
      rememberMe: false,
      captchaUrl: '/captcha',
      errorMessage: ''
    };
  },
  mounted() {
    // 组件挂载后刷新验证码
    this.refreshCaptcha();
  },
  methods: {
    handleLogin() {
      // 清除之前的错误信息
      this.errorMessage = '';
      
      // 构造登录请求数据
      const loginData = {
        username: this.username,
        password: this.password,
        code: this.captcha,
      };

      // 发送登录请求到后端
      this.$http.post('/login', loginData)
        .then(response => {
          // 处理登录响应
          if (response.data.code === 200) {
            // 登录成功后的处理逻辑
            console.log('Login successful!', response.data);
            // 保存 token 到本地存储已在拦截器中处理
            this.$router.push('/home');
          } else {
            // 登录失败的处理逻辑
            this.errorMessage = 'Login failed: ' + response.data.message;
            // 刷新验证码
            this.refreshCaptcha();
          }
        })
        .catch(error => {
          // 处理登录异常
          console.error('Login error:', error);
          if (error.response) {
            // 服务器返回了错误响应
            this.errorMessage = 'Login failed: ' + (error.response.data.message || error.response.statusText);
          } else if (error.request) {
            // 请求已发出但没有收到响应
            this.errorMessage = 'Network error: Unable to connect to server';
          } else {
            // 其他错误
            this.errorMessage = 'Login failed: ' + error.message;
          }
          // 刷新验证码
          this.refreshCaptcha();
        });
    },
    refreshCaptcha() {
      // 添加时间戳以确保图片刷新
      this.captchaUrl = `/captcha?t=${new Date().getTime()}`;
    },
  },
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.form-group img.captcha-image {
  cursor: pointer;
  display: block;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.remember-me-group {
  display: flex;
  align-items: center;
}

.remember-me-group input {
  width: auto;
  margin-right: 8px;
}

.remember-me-group label {
  margin-bottom: 0;
  font-weight: normal;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #0056b3;
}

.error-message {
  color: #dc3545;
  background-color: #f8d7da;
  border: 1px solid #f5c6cb;
  padding: 10px;
  border-radius: 4px;
  margin-top: 15px;
}
</style>