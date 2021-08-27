plugins {
    `android-application`
    kotlin("android")
    kotlin("kapt")
}

androidAppConfig {
    defaultConfig {
        applicationId = "sample.products.detail"
        versionCode = 1
        versionName = "1.0"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
                arg("room.incremental", true)
            }
        }
    }
}

dependencies {
    implementation(project(Modules.Products.detail))
    implementation(project(Modules.Products.local))

    implementation(Dependencies.Fragment.runtime)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.transition)
    implementation(Dependencies.coil)

    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.ArchCore.testing)
    testImplementation(Dependencies.Kotlin.coroutinesTest)

    defaultAndroidTestDependencies()

    kapt(Dependencies.Room.compiler)
}
