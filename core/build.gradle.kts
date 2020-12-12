plugins {
    `java-library`
    kotlin("jvm")
}

jvmConfig()

dependencies {
    implementation(Dependency.Kotlin.stdlib)
    implementation(Dependency.inject)
}