plugins {
    `android-application`
    kotlin("android")
    kotlin("kapt")
    ksp
    hilt
}

androidAppConfig {
    defaultConfig {
        applicationId = "app.products"
        versionCode = 1
        versionName = "1.0"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
                arg("room.incremental", true)
            }
            correctErrorTypes = true // https://dagger.dev/hilt/gradle-setup.html
        }
    }

    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.Utils.ui))
    implementation(project(Modules.Utils.async))
    implementation(project(Modules.Products.list))
    implementation(project(Modules.Products.detail))
    implementation(project(Modules.Products.local))
    implementation(project(Modules.Products.remote))

    implementation(Dependencies.activity)
    implementation(Dependencies.Fragment.runtime)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.material)
    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Dagger.Hilt.runtime)
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.timber)
    implementation(Dependencies.multidex)

    debugImplementation(Dependencies.leakCanary)

    ksp(Dependencies.Room.compiler)
    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.Hilt.compiler)
}
