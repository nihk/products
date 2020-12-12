plugins {
    `android-library`
    kotlin("android")
}

androidLibraryConfig()

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependency.Kotlin.stdlib)
    implementation(Dependency.inject)
    implementation(Dependency.Fragment.runtime)
    implementation(Dependency.Fragment.runtimeKtx)
    implementation(Dependency.recyclerView)
}