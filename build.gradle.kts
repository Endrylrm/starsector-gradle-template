plugins {
    id("org.endrylrm.mod-builder")
}

// Game Directory
val starsectorDir = providers.gradleProperty("starsectorDir").get()

val starsectorJars = files(
    "$starsectorDir/starsector-core/starfarer.api.jar",
    "$starsectorDir/starsector-core/starfarer_obf.jar",
    "$starsectorDir/starsector-core/log4j-1.2.9.jar",
    "$starsectorDir/starsector-core/lwjgl.jar",
    "$starsectorDir/starsector-core/lwjgl_util.jar",
    "$starsectorDir/starsector-core/fs.common_obf.jar"
)

modBuilder {
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
        totalConversion = true

        replace {
            add("data/missions/mission_list.csv")
        }

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

modJars {
    create("exampleMod") {
        outputFilename = "ExampleMod.jar"
        from(project(":ExampleMod"))
    }
}

subprojects {
    pluginManager.withPlugin("java") {
        dependencies {
            add(
                "compileOnly",
                starsectorJars
            )
        }
    }
}
