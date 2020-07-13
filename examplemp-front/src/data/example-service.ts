import { injectable } from 'inversify'
import { examplemp } from 'examplemp'

interface ExampleService {
  fetchExampleData(): string;
}

export type { ExampleService }

@injectable()
export class ExampleDummyService implements ExampleService {
  fetchExampleData (): string {
    return examplemp.doStuff('kotlin', 4)
  }
}

export const ExampleServiceSymbol = Symbol.for('ExampleService')
