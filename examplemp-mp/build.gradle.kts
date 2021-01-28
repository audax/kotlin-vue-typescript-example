@file:Suppress("UNUSED_VARIABLE")

group = "examplemp"
version = "0.1.1"

plugins {
    kotlin("multiplatform")
}
repositories {
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    maven {
        url = uri("https://kotlin.bintray.com/kotlinx")
    }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js(IR) {
        binaries.executable()
        moduleName = "examplemp"
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }

    @Suppress("COMPATIBILITY_WARNING")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

tasks.register<Copy>("jsCopyDistribution") {
    dependsOn("jsBrowserWebpack")
    from("$buildDir/distributions/examplemp-mp.js", "$buildDir/distributions/examplemp-mp.js.map")
    into("${rootProject.buildDir}/dist/examplemp/kotlin")
    rename("examplemp-mp\\.(.+)", "examplemp.$1")
}

tasks.register<Copy>("jsCopyModule") {
    dependsOn("jsCopyDistribution")
    from("${rootProject.buildDir}/js/packages/examplemp")
    exclude("node_modules", "kotlin/examplemp.js")
    into("${rootProject.buildDir}/dist/examplemp")
}

tasks.register("jsNpmModule") {
    group = "build"
    dependsOn("jsCopyModule")
    val moduleName = "examplemp"
    val workDir = "${rootProject.buildDir}/dist/examplemp"
    val path = file("${workDir}/kotlin/${moduleName}.d.ts")
    doLast {
        println("==============================================================")
        println("Making some adjustments to \n$path")
        val newLines = mutableListOf<String>()
        println("==============================================================")
        val content = path.readLines().toMutableList()
        if (content.size > 0) {
            // drop declare namespace wrapper
            if (content[0].startsWith("declare namespace")) {
                content[0] = ""
                content[content.lastIndex] = ""
            }
            for ((i, _) in content.withIndex()) {
                if (content[i].contains("namespace") && !content[i].contains("export namespace")) {
                    content[i] = content[i].replaceFirst("namespace", "export namespace")
                }
                content[i] = content[i].replace("kotlin.Long", "number")
                content[i] = content[i].replace("kotlin.Char", "string")
            }
        }
        path.writeText(content.joinToString(separator = "\n"))
        println("==============================================================")
    }
}
