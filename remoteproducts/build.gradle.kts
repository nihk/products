plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig()

dependencies {
    implementation(Dependency.Retrofit.runtime)
    implementation(Dependency.Retrofit.moshi)
    implementation(Dependency.Moshi.runtime)
    implementation(Dependency.Moshi.adapters)
    implementation(Dependency.Dagger.runtime)

    kapt(Dependency.Dagger.compiler)
    kapt(Dependency.Moshi.kotlinCodegen)
}