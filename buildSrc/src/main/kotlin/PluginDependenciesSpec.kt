import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`android-application`: PluginDependencySpec
    get() = id("com.android.application")

val PluginDependenciesSpec.`android-library`: PluginDependencySpec
    get() = id("com.android.library")

// ./gradlew dependencyUpdates
val PluginDependenciesSpec.`ben-manes-versions`: PluginDependencySpec
    get() = id("com.github.ben-manes.versions") version Version.benManesVersions