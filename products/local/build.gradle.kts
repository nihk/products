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
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Dagger.Hilt.runtime)

    androidTestImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.ArchCore.testing)
    androidTestImplementation(Dependencies.Kotlin.coroutinesTest)
    androidTestImplementation(Dependencies.Room.testing)
    defaultAndroidTestDependencies()

    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.Hilt.compiler)

    kaptAndroidTest(Dependencies.Room.compiler)
}
