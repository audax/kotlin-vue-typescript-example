import com.moowork.gradle.node.yarn.YarnTask
import org.gradle.kotlin.dsl.support.serviceOf
import org.gradle.launcher.daemon.server.scaninfo.DaemonScanInfo

group = "examplemp"
version = "0.1.0"

plugins {
    id("com.github.node-gradle.node")
}


fun failIfRunInDaemon(taskName: String) {
    var runningAsDaemon = false
    try {
        val daemonScanInfo = project.serviceOf<DaemonScanInfo>()
        runningAsDaemon = !daemonScanInfo.isSingleUse
    } catch (ignored: Throwable) {}
    if (runningAsDaemon) {
        throw IllegalStateException(
                "Cannot run watch task in a gradle daemon, run with ./gradlew $taskName --no-daemon")
    }
}

tasks {
    yarn {
        dependsOn(":examplemp-mp:jsNpmModule")
    }
}

tasks.register<YarnTask>("test") {
    dependsOn(":examplemp-mp:jsNpmModule")
    args = listOf("test:unit")
}

tasks.register<YarnTask>("e2etest") {
    dependsOn(":examplemp-mp:jsNpmModule")
    args = listOf("test:e2e")
}

tasks.register<YarnTask>("serve") {
    doFirst {
        failIfRunInDaemon("serve")
    }
    dependsOn(":examplemp-mp:jsNpmModule")
    args = listOf("serve")
}

tasks.register<YarnTask>("build") {
    dependsOn(":examplemp-mp:jsNpmModule")
    args = listOf("build")
}

tasks.register<YarnTask>("lint") {
    args = listOf("lint")
}

tasks.register<YarnTask>("i18nreport") {
    args = listOf("i18n:report")
}

node {
    download = true
    version = "12.18.1"
    yarnVersion = "1.22.0"
}
