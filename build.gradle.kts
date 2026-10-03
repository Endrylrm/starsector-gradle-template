plugins {
    id("java")
	kotlin("jvm") version "2.3.21"
}

val properties = ModProperties(project)

// game directory
val starsectorDir = properties.get("starsectorDir")

// mod_info.json config
val modJarFiles = properties
    .get("modJarList")
    .split(",")
    .map(String::trim)
    .filter(String::isNotEmpty)

val modInfo = ModInfo(
    id = properties.get("modId"),
    name = properties.get("modName"),
    version = properties.get("modVersion"),
    author = properties.get("modAuthor"),
    utility = properties.getOrDefault("modIsUtility", "false").toBoolean(),
    description = properties.get("modDescription"),
    plugin = properties.get("modPlugin"),
    gameVersion = properties.getOrDefault("gameVersion", "0.98a-RC8"),
    jars = modJarFiles,
    dependencies = getModDependencies()
)

val modInfoJson = modInfo.toJson()

// build configuration
val modOutputJar = properties.getOrDefault("modOutputJar", modInfo.id)

group = "org.example"
version = modInfo.version

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
        "$starsectorDir/starsector-core/starfarer.api.jar",
        "$starsectorDir/starsector-core/starfarer_obf.jar",
        "$starsectorDir/starsector-core/log4j-1.2.9.jar",
        "$starsectorDir/starsector-core/lwjgl.jar",
        "$starsectorDir/starsector-core/lwjgl_util.jar",
        "$starsectorDir/starsector-core/fs.common_obf.jar"
    ))
}

tasks.jar {
    archiveFileName.set(modOutputJar)
}

val modInfoFile = layout.buildDirectory.file("generated/mod_info.json")

tasks.register("generateModInfo") {
    outputs.file(modInfoFile)

    doLast {
        modInfoFile.get().asFile.apply {
            parentFile.mkdirs()
            writeText(modInfoJson)
        }
    }
}

tasks.register<Copy>("buildMod") {
    dependsOn(tasks.jar)
    dependsOn("generateModInfo")

    println("Build mod: ${modInfo.name}...")
    val modDir = layout.buildDirectory.dir("mod/${modInfo.id}")

    into(modDir)

    from("mod")

    from(modInfoFile)

    into("jars") {
        from(tasks.jar)
    }
}

tasks.register<Sync>("installMod") {
    dependsOn("buildMod")

    println("Installing mod: ${modInfo.name} in Starsector mods folder...")
    val modDir = layout.buildDirectory.dir("mod/${modInfo.id}")
    val starsectorModsDir = file("$starsectorDir/mods/${modInfo.id}")

    from(modDir)
    into(starsectorModsDir)
}
