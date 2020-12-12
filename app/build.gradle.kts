plugins {
    `android-application`
    kotlin("android")
    kotlin("kapt")
}

androidAppConfig {
    defaultConfig {
        applicationId = "loblaw.app"
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":core"))
    implementation(project(":di"))
    implementation(project(":uiutils"))
    implementation(project(":productlist"))
    implementation(project(":productdetail"))
    implementation(project(":localproducts"))
    implementation(project(":remoteproducts"))

    implementation(Dependency.Kotlin.stdlib)
    implementation(Dependency.Activity.activityKtx)
    implementation(Dependency.appCompat)
    implementation(Dependency.coreKtx)
    implementation(Dependency.vectorDrawable)
    implementation(Dependency.constraintLayout)
    implementation(Dependency.material)
    implementation(Dependency.photoView)
    implementation(Dependency.Navigation.runtimeKtx)
    implementation(Dependency.Navigation.fragmentKtx)
    implementation(Dependency.Navigation.uiKtx)
    implementation(Dependency.Dagger.runtime)
    implementation(Dependency.Retrofit.runtime)
    implementation(Dependency.Retrofit.moshi)
    implementation(Dependency.Moshi.runtime)
    implementation(Dependency.Room.runtime)
    implementation(Dependency.Room.roomKtx)
    implementation(Dependency.timber)
    implementation(Dependency.okhttpLoggingInterceptor)
    implementation(Dependency.multidex)

    kapt(Dependency.Room.compiler)
    kapt(Dependency.Dagger.compiler)
}