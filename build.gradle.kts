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

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
