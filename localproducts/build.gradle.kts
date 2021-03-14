plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
    hilt
}

androidLibraryConfig {
    packagingOptions.excludes.addAll(setOf("META-INF/AL2.0", "META-INF/LGPL2.1"))
}

dependencies {
    implementation(Dependency.Room.runtime)
    implementation(Dependency.Room.roomKtx)
    implementation(Dependency.Dagger.runtime)
    implementation(Dependency.Dagger.Hilt.runtime)

    androidTestImplementation(Dependency.junit)
    androidTestImplementation(Dependency.ArchCore.testing)
    androidTestImplementation(Dependency.Kotlin.coroutinesTest)
    androidTestImplementation(Dependency.Room.testing)
    defaultAndroidTestDependencies()

    kapt(Dependency.Dagger.compiler)
    kapt(Dependency.Dagger.Hilt.compiler)

    kaptAndroidTest(Dependency.Room.compiler)
}
