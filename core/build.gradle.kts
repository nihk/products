plugins {
    `java-library`
    kotlin("jvm")
}

jvmConfig()

dependencies {
    implementation(Dependency.inject)
}