module.exports = {
  devServer: {
    port: 8080,
    proxy: {
      '/captcha': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      },
      '/login': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      },
      '/logout': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      },
      '/getCurrentUser': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      }
    }
  }
};