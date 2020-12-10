plugins {
    `java-library`
    kotlin("jvm")
}

sourceSets["main"].java.srcDir("src/main/kotlin")

dependencies {
    implementation(Dependency.Kotlin.stdlib)
    implementation(Dependency.inject)
}