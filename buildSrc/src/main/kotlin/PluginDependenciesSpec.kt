import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`android-application`: PluginDependencySpec
    get() = id(Plugin.androidApplication)

val PluginDependenciesSpec.`android-library`: PluginDependencySpec
    get() = id(Plugin.androidLibrary)

val PluginDependenciesSpec.hilt: PluginDependencySpec
    get() = id(Plugin.daggerHilt)

// ./gradlew dependencyUpdates
val PluginDependenciesSpec.`ben-manes-versions`: PluginDependencySpec
    get() = id(Plugin.benManesVersions) version Version.benManesVersions

// ./gradlew ktlintCheck
val PluginDependenciesSpec.`ktlint-gradle`: PluginDependencySpec
    get() = id(Plugin.ktlintGradle) version Version.ktlintGradle
