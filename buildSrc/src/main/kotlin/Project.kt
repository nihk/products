import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.androidAppConfig(extras: (BaseAppModuleExtension.() -> Unit)? = null) = androidConfig<BaseAppModuleExtension>().run {
    defaultConfig {
        buildToolsVersion(BuildVersion.buildTools)
        multiDexEnabled = true
    }

    buildFeatures {
        buildConfig = true
    }

    extras?.invoke(this)
}

fun Project.androidLibraryConfig(extras: (LibraryExtension.() -> Unit)? = null) = androidConfig<LibraryExtension>().run {
    buildFeatures {
        buildConfig = false
    }

    extras?.invoke(this)
}

private fun <T : BaseExtension> Project.androidConfig() = android<T>().apply {
    compileSdkVersion(BuildVersion.compileSdk)

    defaultConfig {
        minSdkVersion(BuildVersion.minSdk)
        targetSdkVersion(BuildVersion.targetSdk)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType(KotlinCompile::class.java) {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = listOf(
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
            )
        }
    }

    sourceSets {
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("test").java.srcDir("src/test/kotlin")
        getByName("androidTest").java.srcDir("src/androidTest/kotlin")
    }

    testOptions {
        animationsDisabled = true
    }
}

private fun <T : BaseExtension> Project.android(): T {
    @Suppress("UNCHECKED_CAST")
    return extensions.findByName("android") as T
}

fun Project.jvmConfig() {
    val sourceSets = extensions.getByName("sourceSets") as SourceSetContainer
    sourceSets["main"].java.srcDir("src/main/kotlin")
}