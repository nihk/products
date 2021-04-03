plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig()

dependencies {
    implementation(project(":core"))
    implementation(project(":localproducts"))
    implementation(project(":utils-ui"))

    implementation(Dependencies.inject)
    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Fragment.runtime)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.recyclerView)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.Navigation.runtime)
    implementation(Dependencies.Navigation.fragment)
    implementation(Dependencies.multidex)
    implementation(Dependencies.coil)
    implementation(Dependencies.Lifecycle.viewModel)
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.transition)

    // https://twitter.com/ianhlake/status/1059604904795230209
    debugImplementation(Dependencies.Fragment.testing)

    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.ArchCore.testing)
    testImplementation(Dependencies.Kotlin.coroutinesTest)

    defaultAndroidTestDependencies()

    kapt(Dependencies.Dagger.compiler)
}
