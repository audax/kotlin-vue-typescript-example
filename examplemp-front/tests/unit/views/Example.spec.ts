import { createLocalVue, mount, shallowMount } from '@vue/test-utils'
import Example from '@/views/Example.vue'
import { container, modules } from '@/inversify.config'
import { ExampleServiceSymbol } from '@/data/example-service'
import type { ExampleService } from '@/data/example-service'
import { ContainerModule, injectable } from 'inversify'
import Vuex, { Store } from 'vuex'
import { exampleModule, ExampleState } from '@/store/modules/example'
import { setupI18n } from '../../testI18n'

const localVue = createLocalVue()

const i18n = setupI18n(localVue)
localVue.use(Vuex)

describe('Example.vue', () => {
  let store: Store<ExampleState>

  const createWrapper = () => shallowMount(Example, { store, localVue, i18n })
  const createFullWrapper = () => mount(Example, { store, localVue, i18n })

  afterEach(() => {
    container.unbindAll()
    container.unload(...modules)
    container.load(...modules)
  })

  beforeEach(() => {
    store = new Vuex.Store({
      ...exampleModule,
      state: {
        count: 41
      }
    })
  })

  it('shows the count from the state', () => {
    const wrapper = createWrapper()
    expect(wrapper.text()).toMatch('42')
  })

  it('increments the count on click', async () => {
    const wrapper = createFullWrapper()
    wrapper.find('#state-counter').trigger('click')
    expect(store.state.count).toEqual(42)
    expect(store.getters.countFromOne).toEqual('43')
    await wrapper.vm.$nextTick()
    expect(wrapper.text()).toMatch('43')
  })

  it('shows the message from the service', () => {
    const wrapper = createWrapper()
    expect(wrapper.text()).toMatch('kotlin kotlin kotlin kotlin')
  })

  it('shows a translated message', () => {
    const wrapper = createWrapper()
    expect(wrapper.find('#i18n-test').text()).toMatch('Hello example!')
  })

  it('can use a mocked service', () => {
    @injectable()
    class MockService implements ExampleService {
      fetchExampleData (): string {
        return 'Default Mock Data'
      }
    }

    container.rebind<ExampleService>(ExampleServiceSymbol).to(MockService)
    const wrapper = createWrapper()
    expect(wrapper.text()).toMatch('Default Mock Data')
  })

  it('can use different module', () => {
    @injectable()
    class GloballyMockService implements ExampleService {
      fetchExampleData (): string {
        return 'Global Mock Data'
      }
    }

    const module = new ContainerModule((bind) => {
      bind<ExampleService>(ExampleServiceSymbol).to(GloballyMockService)
    })
    container.unload(...modules)
    container.load(module)
    const wrapper = createWrapper()
    expect(wrapper.text()).toMatch('Global Mock Data')
  })
})
