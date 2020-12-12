plugins {
    `android-library`
    kotlin("android")
}

androidLibraryConfig()

dependencies {
    implementation(Dependency.inject)
    implementation(Dependency.Dagger.runtime)
    implementation(Dependency.Fragment.runtime)
    implementation(Dependency.Fragment.runtimeKtx)
}