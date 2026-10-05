package org.endrylrm.ModBuilder

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.named
import kotlinx.serialization.json.Json

import org.endrylrm.ModBuilder.extensions.ModBuilderExtension
import org.endrylrm.ModBuilder.tasks.BuildModTask
import org.endrylrm.ModBuilder.tasks.GenerateModInfoTask
import org.endrylrm.ModBuilder.tasks.InstallModTask

class ModBuilderPlugin : Plugin<Project>
{
    override fun apply(project: Project)
    {
        val modBuilder = project.extensions.create(
            "ModBuilder",
            ModBuilderExtension::class.java
        )

        val generateModInfo = project.tasks.register<GenerateModInfoTask>("generateModInfo")
        {
            modId.set(modBuilder.getMod().id)
            modName.set(modBuilder.getMod().name)
            modVersion.set(modBuilder.getMod().version)
            modAuthor.set(modBuilder.getMod().author)

            modUtility.set(modBuilder.getMod().utility)
            modDescription.set(modBuilder.getMod().description)

            modPlugin.set(modBuilder.getMod().modPlugin)
            modGameVersion.set(modBuilder.getMod().gameVersion)

            modJars.set(modBuilder.getMod().getJars())
            modDependencies.set(
                Json.encodeToString(
                    modBuilder.getMod().getDependencies()
                )
            )

            outputFile.set(project.layout.buildDirectory.file("generated/mod_info.json"))
        }

        val buildMod = project.tasks.register<BuildModTask>("buildMod")
        {
            generatedModInfo.set(
                generateModInfo.flatMap {
                    it.outputFile
                }
            )

            jarFile.set(
                project.tasks.named<Jar>("jar").flatMap {
                    it.archiveFile
                }
            )

            modDirectory.set(project.layout.projectDirectory.dir("mod"))

            outputDirectory.set(
                project.layout.buildDirectory.dir(
                    "mod/${modBuilder.getBuild().folderName.get()}"
                )
            )

            dependsOn(generateModInfo)
            dependsOn(project.tasks.named("jar"))
        }

        val installMod = project.tasks.register<InstallModTask>("installMod")
        {
            modDirectory.set(
                buildMod.flatMap {
                    it.outputDirectory
                }
            )

            gameModDirectory.set(
                modBuilder.gameDirectory.dir("mods/${modBuilder.getBuild().folderName.get()}")
            )

            dependsOn(buildMod)
        }

        if (modBuilder.getBuild().copyToGame.get())
        {
            project.tasks.named("build") {
                finalizedBy(installMod)
            }
        }
    }
}
