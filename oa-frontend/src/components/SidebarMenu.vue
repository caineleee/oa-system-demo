<template>
  <div class="sidebar-menu">
    <!-- 直接显示所有菜单项 -->
    <div
        v-for="menu in processedMenus"
        :key="`menu-${menu.id}`"
        class="menu-item"
    >
      <!-- 一级菜单 -->
      <div class="menu-level-1">
        <i v-if="menu.icon" :class="menu.icon" class="menu-icon"></i>
        <span class="menu-title">{{ menu.name }}</span>
      </div>

      <!-- 二级菜单 -->
      <div
          v-if="menu.children && menu.children.length > 0"
          class="submenu-container"
      >
        <div
            v-for="child in menu.children"
            :key="`child-${child.id}`"
            class="menu-level-2"
        >
          <router-link
              :to="getMenuPath(child)"
              class="submenu-link"
              active-class="active"
          >
            <span class="submenu-title">{{ child.name }}</span>
          </router-link>
        </div>
      </div>
    </div>

    <!-- 如果没有菜单数据，显示提示 -->
    <div v-if="processedMenus.length === 0" class="no-menu">
      暂无菜单数据
    </div>
  </div>
</template>

<script>
export default {
  name: 'SidebarMenu',
  data() {
    return {
      processedMenus: []
    };
  },
  mounted() {
    this.loadMenus();
  },
  methods: {
    getMenuPath(menu) {
      // 使用path或url，优先使用path
      let path = menu.path || menu.url || '/';
      // 确保路径以/开头
      if (path && !path.startsWith('/')) {
        path = '/' + path;
      }
      return path;
    },

    async loadMenus() {
      try {
        console.log('开始加载菜单数据...');
        const response = await this.$http.get('/system/config/menu');
        console.log('菜单接口响应:', response);

        if (response && response.data) {
          // 直接使用后端返回的数据，不进行复杂处理
          this.processedMenus = this.simpleProcessMenus(response.data);
          console.log('简化处理后的菜单数据:', this.processedMenus);
        } else {
          console.warn('接口返回数据为空');
          this.processedMenus = [];
        }
      } catch (error) {
        console.error('获取菜单失败:', error);
        this.processedMenus = [];
      }
    },

    simpleProcessMenus(menus) {
      if (!menus || !Array.isArray(menus) || menus.length === 0) {
        console.warn('菜单数据为空或格式不正确');
        return [];
      }

      console.log('原始菜单数据:', menus);

      // 直接过滤掉不需要的菜单项
      const filteredMenus = menus.filter(menu => {
        if (!menu || !menu.id) return false;

        // 过滤掉不需要的菜单
        if (menu.name === '所有' || menu.id === 29) return false;

        // 只保留parentId为1的菜单（根据您的数据结构）
        if (menu.parentId === 1) return true;

        return false;
      });

      console.log('过滤后的菜单:', filteredMenus);
      return filteredMenus;
    }
  }
};
</script>

<style scoped>
/* 侧边栏菜单容器 */
.sidebar-menu {
  width: 240px;
  background-color: #ffffff;
  border-right: 1px solid #eaeaea;
  height: calc(100vh - 60px);
  overflow-y: auto;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.05);
}

/* 菜单项 */
.menu-item {
  border-bottom: 1px solid #f0f0f0;
}

/* 一级菜单项 */
.menu-level-1 {
  display: flex;
  align-items: center;
  padding: 16px 20px;
  font-size: 15px;
  font-weight: 600;
  color: #333333;
  background-color: #f8fafc;
  border-left: 4px solid #2d8cf0;
}

.menu-icon {
  margin-right: 10px;
  font-size: 16px;
  color: #2d8cf0;
  width: 16px;
  text-align: center;
}

.menu-title {
  flex: 1;
}

/* 二级菜单容器 */
.submenu-container {
  background-color: #ffffff;
}

/* 二级菜单项 */
.menu-level-2 {
  padding: 0;
  border-left: 4px solid transparent;
}

.submenu-link {
  display: block;
  padding: 12px 20px 12px 40px;
  color: #666666;
  text-decoration: none;
  font-size: 14px;
  transition: all 0.2s ease;
  border-bottom: 1px solid #f9f9f9;
}

.submenu-link:hover {
  background-color: #f0f7ff;
  color: #2d8cf0;
}

.submenu-link.active {
  background-color: #e1f0ff;
  color: #2d8cf0;
  font-weight: 500;
}

/* 无菜单数据提示 */
.no-menu {
  padding: 20px;
  text-align: center;
  color: #999;
  font-size: 14px;
}

/* 滚动条样式 */
.sidebar-menu::-webkit-scrollbar {
  width: 6px;
}

.sidebar-menu::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.sidebar-menu::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.sidebar-menu::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style>