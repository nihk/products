plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
    hilt
}

androidLibraryConfig()

dependencies {
    implementation(project(":core"))
    implementation(project(":remoteproducts"))
    implementation(project(":localproducts"))
    implementation(project(":uiutils"))

    implementation(Dependency.inject)
    implementation(Dependency.Dagger.runtime)
    implementation(Dependency.Dagger.Hilt.runtime)
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
    implementation(Dependency.savedState)

    // https://twitter.com/ianhlake/status/1059604904795230209
    debugImplementation(Dependency.Fragment.testing)

    testImplementation(Dependency.junit)
    testImplementation(Dependency.ArchCore.testing)
    testImplementation(Dependency.Kotlin.coroutinesTest)

    androidTestImplementation(project(":testutils"))
    defaultAndroidTestDependencies()
    androidTestImplementation(Dependency.Moshi.runtime)
    androidTestImplementation(Dependency.Moshi.adapters)

    kapt(Dependency.Dagger.compiler)
    kapt(Dependency.Dagger.Hilt.compiler)

    kaptAndroidTest(Dependency.Room.compiler)
}
