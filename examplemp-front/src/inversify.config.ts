import { Container, ContainerModule } from 'inversify'
import { ExampleDummyService, ExampleServiceSymbol } from '@/data/example-service'
import type { ExampleService } from '@/data/example-service'
import getDecorators from 'inversify-inject-decorators'

const dataModule = new ContainerModule(
  (bind) => {
    bind<ExampleService>(ExampleServiceSymbol).to(ExampleDummyService)
  }
)

const modules = [dataModule]
const container = new Container()
container.load(...modules)

const { lazyInject } = getDecorators(container, false)

export { container, lazyInject, modules }
