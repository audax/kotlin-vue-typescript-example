import VueI18n, { LocaleMessages } from 'vue-i18n'
import type { VueConstructor } from 'vue'

function loadLocaleMessages (): LocaleMessages {
  return {
    de: require('@/locales/de.json')
  }
}

export function setupI18n (vueInstance: VueConstructor) {
  vueInstance.use(VueI18n)
  return new VueI18n({
    locale: 'de',
    fallbackLocale: 'en',
    messages: loadLocaleMessages()
  })
}
