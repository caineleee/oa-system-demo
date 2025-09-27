<template>
  <div class="home-container">
    <!-- 页面头部 -->
    <header>
      <h1>OA System</h1>
      <!-- 用户菜单容器 -->
      <div class="user-menu-container" @click="toggleUserMenu" v-click-outside="hideUserMenu">
        <!-- 用户头像 -->
        <img :src="user.avatar" alt="User Avatar" class="user-avatar" />
        <!-- 用户菜单 -->
        <div v-if="userMenuVisible" class="user-menu">
          <!-- 登出菜单项 -->
          <div class="user-menu-item" @click="logout">
            <i class="menu-icon">mdi-logout</i>
            <span>Logout</span>
          </div>
        </div>
      </div>
    </header>
    <!-- 页面主体内容 -->
    <main>
      <h1>Welcome to OA System</h1>
      <p>This is the home page. You have successfully logged in.</p>
    </main>
  </div>
</template>

<script>
// 首页组件
export default {
  // 组件数据
  data() {
    return {
      // 用户信息
      user: {
        // 用户名
        name: 'Guest',
        // 用户头像占位图
        avatar: 'https://placehold.co/64x64', 
      },
      // 用户菜单显示状态
      userMenuVisible: false
    };
  },
  // 组件挂载后执行
  mounted() {
    // 组件挂载后获取用户信息
    this.getCurrentUser();
  },
  // 自定义指令
  directives: {
    // 点击外部区域指令
    'click-outside': {
      // 指令绑定时执行
      bind(el, binding, vnode) {
        el.clickOutsideEvent = function(event) {
          // 检查点击事件是否在元素外部
          if (!(el === event.target || el.contains(event.target))) {
            // 调用指令绑定的方法
            vnode.context[binding.expression](event);
          }
        };
        // 添加点击事件监听器
        document.body.addEventListener('click', el.clickOutsideEvent);
      },
      // 指令解绑时执行
      unbind(el) {
        // 移除点击事件监听器
        document.body.removeEventListener('click', el.clickOutsideEvent);
      }
    }
  },
  // 组件方法
  methods: {
    // 获取当前用户信息
    getCurrentUser() {
      // 获取当前用户信息
      this.$http.get('/getCurrentUser')
        .then(response => {
          // 处理响应数据
          if (response.data) {
            // 设置用户名
            this.user.name = response.data.name || response.data.username;
            // 设置用户头像
            if (response.data.userFace) {
              this.user.avatar = response.data.userFace;
            }
          }
        })
        .catch(error => {
          // 处理错误情况
          console.error('获取用户信息失败:', error);
          if (error.response) {
            // 服务器返回了错误响应
            this.backendError = '获取用户信息失败: ' + (error.response.data.message || error.response.statusText);
          } else if (error.request) {
            // 请求已发出但没有收到响应
            this.backendError = '无法连接到服务器，请确保后端服务正在运行';
          } else {
            // 其他错误
            this.backendError = '获取用户信息失败: ' + error.message;
          }
        });
    },
    // 切换用户菜单显示状态
    toggleUserMenu() {
      this.userMenuVisible = !this.userMenuVisible;
    },
    // 隐藏用户菜单
    hideUserMenu() {
      this.userMenuVisible = false;
    },
    // 用户登出
    logout() {
      // 注销功能
      this.$http.post('/logout')
        .then(() => {
          // 清除本地存储的 token
          localStorage.removeItem('token');
          // 跳转到登录页
          this.$router.push('/login');
        })
        .catch(error => {
          // 处理注销错误
          console.error('注销失败:', error);
          // 即使后端注销失败，也清除本地 token 并跳转到登录页
          localStorage.removeItem('token');
          this.$router.push('/login');
        });
    },
  },
};
</script>

<style scoped>
/* 主容器样式 */
.home-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

/* 头部样式 */
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: #f8f9fa;
  border-bottom: 1px solid #dee2e6;
}

/* 头部标题样式 */
header h1 {
  margin: 0;
  font-size: 1.5rem;
  color: #333;
}

/* 用户菜单容器样式 */
.user-menu-container {
  position: relative;
  cursor: pointer;
}

/* 用户头像样式 */
.user-avatar {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #ddd;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s ease;
}

/* 用户头像悬停样式 */
.user-avatar:hover {
  border-color: #007bff;
}

/* 用户菜单样式 */
.user-menu {
  position: absolute;
  top: 80px; /* 调整菜单顶部距离 */
  right: 0;
  width: 200px; /* 增加菜单宽度 */
  background-color: white;
  border-radius: 8px; /* 增加圆角半径 */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15); /* 增加阴影效果 */
  z-index: 1000;
  animation: fadeIn 0.2s ease;
}

/* 用户菜单项样式 */
.user-menu-item {
  display: flex;
  align-items: center;
  padding: 14px 20px; /* 调整内边距 */
  cursor: pointer;
  transition: background-color 0.2s;
  color: #333;
  font-size: 16px; /* 增加字体大小 */
}

/* 用户菜单项悬停样式 */
.user-menu-item:hover {
  background-color: #f8f9fa;
}

/* 菜单图标样式 */
.menu-icon {
  margin-right: 12px; /* 增加图标与文字之间的间距 */
  font-size: 18px; /* 增加图标大小 */
}

/* 主体内容样式 */
main {
  flex: 1;
  padding: 20px;
}

/* 错误消息样式 */
.error-message {
  color: #dc3545;
  background-color: #f8d7da;
  border: 1px solid #f5c6cb;
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 15px;
}

/* 淡入动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>