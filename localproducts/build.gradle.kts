plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(BuildVersion.compileSdk)

    defaultConfig {
        minSdkVersion(BuildVersion.minSdk)
        targetSdkVersion(BuildVersion.targetSdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
        )
    }

    sourceSets {
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("androidTest").java.srcDir("src/androidTest/kotlin")
    }

    packagingOptions.excludes.addAll(setOf("META-INF/AL2.0", "META-INF/LGPL2.1"))
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

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