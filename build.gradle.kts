buildscript {
    repositories.addProjectDefaults()
    dependencies {
        classpath(Dependency.androidGradlePlugin)
        classpath(Dependency.Kotlin.plugin)
    }
}

plugins {
    `ben-manes-versions`
}

allprojects {
    repositories.addProjectDefaults()
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
