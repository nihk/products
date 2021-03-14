buildscript {
    repositories.addProjectDefaults()
    dependencies {
        classpath(Dependency.androidGradlePlugin)
        classpath(Dependency.Kotlin.plugin)
        classpath(Dependency.Dagger.Hilt.plugin)
    }
}

plugins {
    `ben-manes-versions`
    `ktlint-gradle`
}

allprojects {
    repositories.addProjectDefaults()
    apply(plugin = Plugin.ktlintGradle)
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
