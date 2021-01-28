plugins {
    kotlin("multiplatform") version "1.4-M3" apply false
    id("com.github.node-gradle.node") version "2.2.0" apply false
}
// a bad change.
repositories {
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}
