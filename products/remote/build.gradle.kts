plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
    ksp
    hilt
}

androidLibraryConfig()

dependencies {
    implementation(project(Modules.core))

    implementation(Dependencies.Retrofit.runtime)
    implementation(Dependencies.Retrofit.moshi)
    implementation(Dependencies.OkHttp.loggingInterceptor)
    implementation(Dependencies.Moshi.runtime)
    implementation(Dependencies.Moshi.adapters)
    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Dagger.Hilt.runtime)

    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.Hilt.compiler)
    ksp(Dependencies.Moshi.kotlinCodegen)
}
