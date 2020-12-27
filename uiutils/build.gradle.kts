plugins {
    `android-library`
    kotlin("android")
}

androidLibraryConfig()

dependencies {
    implementation(Dependency.inject)
    implementation(Dependency.Fragment.runtime)
    implementation(Dependency.Fragment.runtimeKtx)
    implementation(Dependency.recyclerView)
    implementation(Dependency.Navigation.uiKtx)
}