plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig {
    packagingOptions.excludes.addAll(setOf("META-INF/AL2.0", "META-INF/LGPL2.1"))
}

dependencies {
    implementation(Dependency.Room.runtime)
    implementation(Dependency.Room.roomKtx)
    implementation(Dependency.Dagger.runtime)

    androidTestImplementation(Dependency.junit)
    androidTestImplementation(Dependency.ArchCore.testing)
    androidTestImplementation(Dependency.Kotlin.coroutinesTest)
    androidTestImplementation(Dependency.Room.testing)
    androidTestImplementation(Dependency.Espresso.core)
    androidTestImplementation(Dependency.Espresso.contrib)
    androidTestImplementation(Dependency.AndroidTest.core)
    androidTestImplementation(Dependency.AndroidTest.coreKtx)
    androidTestImplementation(Dependency.AndroidTest.extJunit)
    androidTestImplementation(Dependency.AndroidTest.runner)
    androidTestImplementation(Dependency.AndroidTest.rules)

    kapt(Dependency.Dagger.compiler)

    kaptAndroidTest(Dependency.Room.compiler)
}