plugins {
    id("java")
    kotlin("jvm") version "2.3.21"
}

val starsectorDir = providers.gradleProperty("starsectorDir").get()

group = "org.example"
version = "0.1.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(files(
        "${starsectorDir}/starsector-core/starfarer.api.jar",
        "${starsectorDir}/starsector-core/starfarer_obf.jar",
        "${starsectorDir}/starsector-core/log4j-1.2.9.jar",
        "${starsectorDir}/starsector-core/lwjgl.jar",
        "${starsectorDir}/starsector-core/lwjgl_util.jar",
        "${starsectorDir}/starsector-core/fs.common_obf.jar"
    ))

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}