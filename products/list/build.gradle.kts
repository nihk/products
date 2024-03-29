plugins {
    `android-library`
    ksp
    kotlin("android")
}

androidLibraryConfig {
    addSharedTestDirectory("sharedTest")
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.Products.remote))
    implementation(project(Modules.Products.local))
    implementation(project(Modules.Utils.mvi))
    implementation(project(Modules.Utils.ui))

    implementation(Dependencies.inject)
    implementation(Dependencies.Fragment.runtime)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.recyclerView)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.multidex)
    implementation(Dependencies.coil)
    implementation(Dependencies.Lifecycle.viewModel)
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.savedState)

    // https://twitter.com/ianhlake/status/1059604904795230209
    debugImplementation(Dependencies.Fragment.testing)

    testImplementation(project(Modules.Utils.test))
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.ArchCore.testing)
    testImplementation(Dependencies.Kotlin.coroutinesTest)
    testImplementation(Dependencies.Moshi.runtime)
    testImplementation(Dependencies.Moshi.adapters)

    defaultAndroidTestDependencies()
    androidTestImplementation(project(Modules.Utils.test))
    androidTestImplementation(Dependencies.Moshi.runtime)
    androidTestImplementation(Dependencies.Moshi.adapters)

    kspAndroidTest(Dependencies.Room.compiler)
}
