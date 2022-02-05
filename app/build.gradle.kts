plugins {
    `android-application`
    kotlin("android")
    ksp
}

androidAppConfig {
    defaultConfig {
        applicationId = "app.products"
        versionCode = 1
        versionName = "1.0"
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
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.timber)
    implementation(Dependencies.multidex)
    implementation(Dependencies.coil)

    debugImplementation(Dependencies.leakCanary)

    ksp(Dependencies.Room.compiler)
}
