plugins {
    `android-library`
    kotlin("android")
    ksp
}

androidLibraryConfig()

dependencies {
    implementation(project(Modules.core))

    implementation(Dependencies.Retrofit.runtime)
    implementation(Dependencies.Retrofit.moshi)
    implementation(Dependencies.OkHttp.loggingInterceptor)
    implementation(Dependencies.Moshi.runtime)
    implementation(Dependencies.Moshi.adapters)

    ksp(Dependencies.Moshi.kotlinCodegen)
}
