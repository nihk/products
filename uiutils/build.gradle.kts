plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
    hilt
}

androidLibraryConfig()

dependencies {
    implementation(Dependency.inject)
    implementation(Dependency.Fragment.runtime)
    implementation(Dependency.recyclerView)
    implementation(Dependency.coil)
    implementation(Dependency.Dagger.runtime)
    implementation(Dependency.Dagger.Hilt.runtime)
    implementation(Dependency.transition)

    kapt(Dependency.Dagger.compiler)
    kapt(Dependency.Dagger.Hilt.compiler)
}
