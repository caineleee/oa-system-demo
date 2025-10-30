// Vue CLI 配置文件
// 用于配置开发服务器和构建选项
module.exports = {
  // 开发服务器配置
  devServer: {
    // 开发服务器端口
    port: 8080,
    // 代理配置，解决开发环境跨域问题
    proxy: {
      // 验证码接口代理
      '/captcha': {
        // 目标服务器地址
        target: 'http://localhost:8081',
        // 是否改变源地址
        changeOrigin: true,
        // 是否支持https
        secure: false,
        // 日志级别
        logLevel: 'debug'
      },
      // 登录接口代理
      '/login': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      },
      // 登出接口代理
      '/logout': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      },
      // 获取当前用户信息接口代理
      '/user': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      },
      // 系统配置相关接口代理
      '/system': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      },
      // API接口代理 (通用)
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      }
    }
  }
};