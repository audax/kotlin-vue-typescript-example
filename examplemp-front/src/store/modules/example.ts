export interface ExampleState {
  count: number;
}

export const exampleGetters = {
  countFromOne: (state: ExampleState) => (state.count + 1).toString()
}

export const exampleMutations = {
  EXAMPLE_COUNT_INC (state: ExampleState) {
    state.count++
  }
}

export const exampleModule = {
  state: {
    count: 0
  } as ExampleState,
  getters: exampleGetters,
  mutations: exampleMutations
}
