module.exports = {
  chainWebpack: config => {
    config.module
        .rule('images')
        .use('url-loader')
        .loader('url-loader')
        .tap(options => Object.assign(options, {limit: Infinity}))
  },
  assetsDir: 'assets',
  outputDir: './src/main/resources',
  configureWebpack: {
    entry: {
      app: './src/main/vue/main.js'
    }
  }
};
