plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig()

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependency.Retrofit.runtime)
    implementation(Dependency.Retrofit.moshi)
    implementation(Dependency.Moshi.runtime)
    implementation(Dependency.Moshi.adapters)
    implementation(Dependency.Dagger.runtime)

    kapt(Dependency.Dagger.compiler)
    kapt(Dependency.Moshi.kotlinCodegen)
}