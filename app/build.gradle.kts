plugins {
    `android-application`
    kotlin("android")
    kotlin("kapt")
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
    implementation(Dependencies.appCompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.vectorDrawable)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.material)
    implementation(Dependencies.Navigation.runtime)
    implementation(Dependencies.Navigation.fragment)
    implementation(Dependencies.Navigation.ui)
    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Dagger.Hilt.runtime)
    implementation(Dependencies.Retrofit.runtime)
    implementation(Dependencies.Retrofit.moshi)
    implementation(Dependencies.Moshi.runtime)
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.timber)
    implementation(Dependencies.OkHttp.loggingInterceptor)
    implementation(Dependencies.multidex)
    implementation(Dependencies.coil)

    debugImplementation(Dependencies.leakCanary)

    kapt(Dependencies.Room.compiler)
    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.Hilt.compiler)
}
