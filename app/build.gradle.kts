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
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":uiutils"))
    implementation(project(":productlist"))
    implementation(project(":productdetail"))
    implementation(project(":localproducts"))
    implementation(project(":remoteproducts"))

    implementation(Dependency.activity)
    implementation(Dependency.appCompat)
    implementation(Dependency.coreKtx)
    implementation(Dependency.vectorDrawable)
    implementation(Dependency.constraintLayout)
    implementation(Dependency.material)
    implementation(Dependency.Navigation.runtime)
    implementation(Dependency.Navigation.fragment)
    implementation(Dependency.Navigation.ui)
    implementation(Dependency.Dagger.runtime)
    implementation(Dependency.Dagger.Hilt.runtime)
    implementation(Dependency.Retrofit.runtime)
    implementation(Dependency.Retrofit.moshi)
    implementation(Dependency.Moshi.runtime)
    implementation(Dependency.Room.runtime)
    implementation(Dependency.Room.roomKtx)
    implementation(Dependency.timber)
    implementation(Dependency.OkHttp.loggingInterceptor)
    implementation(Dependency.multidex)
    implementation(Dependency.coil)

    debugImplementation(Dependency.leakCanary)

    kapt(Dependency.Room.compiler)
    kapt(Dependency.Dagger.compiler)
    kapt(Dependency.Dagger.Hilt.compiler)
}