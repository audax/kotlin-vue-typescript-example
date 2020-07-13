# Vue.js + TypeScript and a Kotlin multi platform module

Notable stuff:

## project root

The `package.json` creates a yarn workspace to directly include the kotlin-mp module.

## examplemp-mp

A Kotlin multiplatform module with only code in the common source root. The gradle task `jsNpmModule` builds
an npm module in `$projectRoot/dist/examplemp` with modified typescript headers.

## examplemp-front

A Vue.js application with plenty of modules, most of which are irrelevant for this
example, but I don't care enough to remove them.
The relevant part of the `package.json` is the inclusion of the `examplemp` dependencies which is fetched via
the yarn workspace.

The gradle code is just glue code to ensure that the `examplemp-mp` module is available.
