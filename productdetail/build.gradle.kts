plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig {
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(project(":core"))
    implementation(project(":localproducts"))
    implementation(project(":uiutils"))

    implementation(Dependency.inject)
    implementation(Dependency.Dagger.runtime)
    implementation(Dependency.Fragment.runtime)
    implementation(Dependency.Fragment.runtimeKtx)
    implementation(Dependency.appCompat)
    implementation(Dependency.material)
    implementation(Dependency.recyclerView)
    implementation(Dependency.coreKtx)
    implementation(Dependency.constraintLayout)
    implementation(Dependency.Navigation.runtimeKtx)
    implementation(Dependency.Navigation.fragmentKtx)
    implementation(Dependency.multidex)
    implementation(Dependency.coil)
    implementation(Dependency.Lifecycle.liveDataKtx)
    implementation(Dependency.Lifecycle.viewModelKtx)
    implementation(Dependency.Room.runtime)
    implementation(Dependency.Room.roomKtx)

    // https://twitter.com/ianhlake/status/1059604904795230209
    debugImplementation(Dependency.Fragment.testing)

    testImplementation(Dependency.junit)
    testImplementation(Dependency.ArchCore.testing)
    testImplementation(Dependency.Kotlin.coroutinesTest)

    androidTestImplementation(Dependency.Espresso.core)
    androidTestImplementation(Dependency.Espresso.contrib)
    androidTestImplementation(Dependency.AndroidTest.core)
    androidTestImplementation(Dependency.AndroidTest.coreKtx)
    androidTestImplementation(Dependency.AndroidTest.extJunit)
    androidTestImplementation(Dependency.AndroidTest.runner)
    androidTestImplementation(Dependency.AndroidTest.rules)
    androidTestImplementation(Dependency.Mockito.core)
    androidTestImplementation(Dependency.Mockito.android)

    kapt(Dependency.Dagger.compiler)
}