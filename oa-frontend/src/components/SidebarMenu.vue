<template>
  <div class="sidebar-menu">
    <!-- 直接显示所有菜单项 -->
    <div
        v-for="menu in processedMenus"
        :key="`menu-${menu.id}`"
        class="menu-item"
    >
      <!-- 一级菜单 -->
      <div class="menu-level-1" @click="toggleMenu(menu)">
        <i v-if="menu.icon" :class="menu.icon" class="menu-icon"></i>
        <span class="menu-title">{{ menu.name }}</span>
        <i 
          v-if="menu.children && menu.children.length > 0" 
          class="arrow-icon"
          :class="{ 'rotated': menu.expanded }"
        >▼</i>
      </div>

      <!-- 二级菜单 -->
      <transition name="slide">
        <div
            v-if="menu.children && menu.children.length > 0 && menu.expanded"
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
      </transition>
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

      // 过滤掉 id=1 和 id=29 的菜单项
      const filteredMenus = menus.filter(menu => {
        // 过滤掉 id 为 1 和 29 的菜单
        return menu && menu.id && menu.id !== 1 && menu.id !== 29;
      });

      // 为每个菜单项添加expanded属性，默认为false（不展开）
      const processedMenus = filteredMenus.map(menu => {
        return {
          ...menu,
          expanded: false // 默认不展开二级菜单
        };
      });

      console.log('过滤并处理后的菜单数据:', processedMenus);
      return processedMenus;
    },

    // 切换菜单展开状态
    toggleMenu(menu) {
      // 只有当菜单有子菜单时才切换展开状态
      if (menu.children && menu.children.length > 0) {
        menu.expanded = !menu.expanded;
      }
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
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.menu-level-1:hover {
  background-color: #eef7ff;
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

.arrow-icon {
  font-size: 12px;
  color: #999;
  transition: transform 0.3s ease;
}

.arrow-icon.rotated {
  transform: rotate(180deg);
}

/* 二级菜单容器 */
.submenu-container {
  background-color: #ffffff;
}

/* 展开动画 */
.slide-enter-active,
.slide-leave-active {
  transition: all 0.3s ease;
  max-height: 500px;
  overflow: hidden;
}

.slide-enter,
.slide-leave-to {
  max-height: 0;
  opacity: 0;
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