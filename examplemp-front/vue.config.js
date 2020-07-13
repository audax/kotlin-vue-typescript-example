module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  pluginOptions: {
    i18n: {
      locale: 'de',
      fallbackLocale: 'de',
      localeDir: 'locales',
      enableInSFC: false
    }
  },
  chainWebpack: (config) => {
    config.resolve.set('symlinks', false)
  }
}
