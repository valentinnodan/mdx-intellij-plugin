plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.3.0"
}

group "org.intellij.plugin.mdx"

val realVersion = "1.0.213"
version = realVersion

repositories {
    mavenCentral()
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    pluginName.set("MDX")
    version.set("LATEST-EAP-SNAPSHOT")
    type.set("IU")
    plugins.set(listOf("JavaScriptLanguage", "org.intellij.plugins.markdown", "CSS"))
    downloadSources.set(true)
    updateSinceUntilBuild.set(true)
}

tasks {

    patchPluginXml {
        sinceBuild.set("213.0")
        untilBuild.set("213.*")
        version.set(realVersion)
    }

    wrapper {
        gradleVersion = "7.2"
    }

}
