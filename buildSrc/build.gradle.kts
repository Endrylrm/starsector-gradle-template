plugins {
    `kotlin-dsl`
    kotlin("plugin.serialization") version "2.3.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}
