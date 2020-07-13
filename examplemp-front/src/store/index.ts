import Vue from 'vue'
import Vuex from 'vuex'
import { exampleModule } from '@/store/modules/example'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    example: exampleModule
  }
})
