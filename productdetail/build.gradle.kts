plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig()

dependencies {
    implementation(project(":core"))
    implementation(project(":localproducts"))
    implementation(project(":uiutils"))

    implementation(Dependency.inject)
    implementation(Dependency.Dagger.runtime)
    implementation(Dependency.Fragment.runtime)
    implementation(Dependency.appCompat)
    implementation(Dependency.material)
    implementation(Dependency.recyclerView)
    implementation(Dependency.coreKtx)
    implementation(Dependency.constraintLayout)
    implementation(Dependency.Navigation.runtime)
    implementation(Dependency.Navigation.fragment)
    implementation(Dependency.multidex)
    implementation(Dependency.coil)
    implementation(Dependency.Lifecycle.viewModel)
    implementation(Dependency.Room.runtime)
    implementation(Dependency.Room.roomKtx)
    implementation(Dependency.transition)

    // https://twitter.com/ianhlake/status/1059604904795230209
    debugImplementation(Dependency.Fragment.testing)

    testImplementation(Dependency.junit)
    testImplementation(Dependency.ArchCore.testing)
    testImplementation(Dependency.Kotlin.coroutinesTest)

    defaultAndroidTestDependencies()

    kapt(Dependency.Dagger.compiler)
}
