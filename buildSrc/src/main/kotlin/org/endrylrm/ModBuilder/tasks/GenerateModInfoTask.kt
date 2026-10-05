package org.endrylrm.ModBuilder.tasks

import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import org.endrylrm.ModBuilder.model.ModDependencyInfo
import org.endrylrm.ModBuilder.model.ModInfo

abstract class GenerateModInfoTask : DefaultTask()
{
    @get:Input
    abstract val modId: Property<String>

    @get:Input
    abstract val modName: Property<String>

    @get:Input
    abstract val modVersion: Property<String>

    @get:Input
    abstract val modAuthor: Property<String>

    @get:Input
    abstract val modUtility: Property<Boolean>

    @get:Input
    abstract val modDescription: Property<String>

    @get:Input
    abstract val modPlugin: Property<String>

    @get:Input
    abstract val modGameVersion: Property<String>

    @get:Input
    abstract val modJars: ListProperty<String>

    @get:Input
    abstract val modDependencies: Property<String>

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    private val json = Json {
        prettyPrint = true
        explicitNulls = false
    }

    @TaskAction
    fun generate() {
        val dependencies = Json.decodeFromString<List<ModDependencyInfo>>(
            modDependencies.get()
        )

        val modInfo = ModInfo(
            id = modId.get(),
            name = modName.get(),
            version = modVersion.get(),
            author = modAuthor.get(),
            utility = modUtility.get(),
            description = modDescription.get(),
            gameVersion = modGameVersion.get(),
            jars = modJars.get(),
            modPlugin = modPlugin.get(),
            dependencies = dependencies.takeIf { it.isNotEmpty() }
        )

        outputFile.get().asFile.apply {
            parentFile.mkdirs()
            writeText(json.encodeToString(modInfo))
        }
    }
}
