<template>
    <v-card
      class="mx-auto"
      max-width="400"
      tile
    >
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>
            Data from service
          </v-list-item-title>
          <v-list-item-subtitle>
            {{ exampleData }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line >
        <v-list-item-content>
          <v-list-item-title>
            <v-btn text id="state-counter" @click="click">Counter from state</v-btn>
          </v-list-item-title>
          <v-list-item-subtitle>
            {{ count }}
        </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list-item three-line>
        <v-list-item-content>
          <v-list-item-title>Translated text</v-list-item-title>
          <v-list-item-subtitle id="i18n-test">
            {{ $t('example')}}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-card>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { ExampleServiceSymbol } from '@/data/example-service'
import type { ExampleService } from '@/data/example-service'
import { lazyInject } from '@/inversify.config'
@Component
export default class Example extends Vue {
  @lazyInject(ExampleServiceSymbol)
  private exampleService!: ExampleService

  get exampleData () {
    return this.exampleService.fetchExampleData()
  }

  get count () {
    return this.$store.getters.countFromOne
  }

  click () {
    this.$store.commit('EXAMPLE_COUNT_INC')
  }
}
</script>
