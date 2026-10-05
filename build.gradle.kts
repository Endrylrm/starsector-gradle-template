plugins {
    id("java")
	kotlin("jvm") version "2.3.21"
    id("org.endrylrm.mod-builder")
}

// Game Directory
val starsectorDir = file("E:/Fractal Softworks/Starsector")

modBuilder {
    gameDirectory = starsectorDir

    mod {
        id = "example_mod"
        name = "Example Mod"
        version = "0.1.0"
        author = "Example Author"
        utility = false
        description = "Example mod description."
        gameVersion = "0.98a-RC8"

        jars {
            add("jars/ExampleMod.jar")
        }

        modPlugin = "org.example.ExampleModPlugin"

        /*
        dependencies {
            add {
                id = "dependency_id"
                name = "Dependency Name"
            }

            add {
                id = "dependency_id_2"
                name = "Dependency Name 2"
                version = "0.1.0"
            }
        } */
    }

    build {
        folderName = "ExampleMod"
        copyToGame = false
    }
}

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
        "${starsectorDir.absolutePath}/starsector-core/starfarer.api.jar",
        "${starsectorDir.absolutePath}/starsector-core/starfarer_obf.jar",
        "${starsectorDir.absolutePath}/starsector-core/log4j-1.2.9.jar",
        "${starsectorDir.absolutePath}/starsector-core/lwjgl.jar",
        "${starsectorDir.absolutePath}/starsector-core/lwjgl_util.jar",
        "${starsectorDir.absolutePath}/starsector-core/fs.common_obf.jar"
    ))
}

tasks.jar {
    archiveFileName.set("ExampleMod.jar")
}
