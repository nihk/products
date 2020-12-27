plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig()

dependencies {
    implementation(Dependency.inject)
    implementation(Dependency.Fragment.runtime)
    implementation(Dependency.Fragment.runtimeKtx)
    implementation(Dependency.recyclerView)
    implementation(Dependency.Navigation.uiKtx)
    implementation(Dependency.coil)
    implementation(Dependency.Dagger.runtime)

    kapt(Dependency.Dagger.compiler)
}