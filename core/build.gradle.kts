plugins {
    `java-library`
    kotlin("jvm")
}

jvmConfig()

dependencies {
    implementation(Dependencies.inject)
}
