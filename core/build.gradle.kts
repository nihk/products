plugins {
    `android-library`
    kotlin("android")
}

androidLibraryConfig()

dependencies {
    implementation(Dependencies.inject)
    implementation(Dependencies.Fragment.runtime)
}
