package org.endrylrm.ModBuilder

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import kotlinx.serialization.json.Json

import org.endrylrm.ModBuilder.extensions.ModBuilderExtension
import org.endrylrm.ModBuilder.extensions.ModJarExtension
import org.endrylrm.ModBuilder.tasks.BuildModTask
import org.endrylrm.ModBuilder.tasks.GenerateModInfoTask
import org.endrylrm.ModBuilder.tasks.InstallModTask
import org.gradle.api.tasks.Sync

class ModBuilderPlugin : Plugin<Project>
{
    override fun apply(project: Project)
    {
        val modBuilder = project.extensions.create(
            "modBuilder",
            ModBuilderExtension::class.java
        )

        // Mod Jar creation from projects
        val modJarContainer = project.objects.domainObjectContainer(ModJarExtension::class.java)
        project.extensions.add(
            "modJars",
            modJarContainer
        )

        val modJarFiles = project.files()
        val modJarTasks = mutableListOf<TaskProvider<Sync>>()

        modJarContainer.configureEach {
            val modJar = this

            val sourceJar = project.provider {
                project.project(modJar.sourceProject.get())
            }.flatMap {
                it.tasks.named<Jar>("jar")
            }

            val task = project.tasks.register<Sync>(name) {
                dependsOn(sourceJar)

                from(sourceJar) {
                    rename {
                        modJar.outputFilename.get()
                    }
                }

                into(project.layout.buildDirectory.dir("libs"))

                outputs.upToDateWhen { false }
            }

            modJarFiles.from(task)
            modJarTasks += task
        }

        val generateModInfo = project.tasks.register<GenerateModInfoTask>("generateModInfo")
        {
            modId.set(modBuilder.getMod().id)
            modName.set(modBuilder.getMod().name)
            modVersion.set(modBuilder.getMod().version)
            modAuthor.set(modBuilder.getMod().author)
            modUtility.set(modBuilder.getMod().utility.map { it.toString() })
            modDescription.set(modBuilder.getMod().description)
            modGameVersion.set(modBuilder.getMod().gameVersion)
            modPlugin.set(modBuilder.getMod().modPlugin)
            modTotalConversion.set(modBuilder.getMod().totalConversion.map { it.toString() })

            modJars.set(modBuilder.getMod().getJars())
            modDependencies.set(
                Json.encodeToString(
                    modBuilder.getMod().getDependencies()
                )
            )
            modReplace.set(modBuilder.getMod().getReplace())

            outputFile.set(project.layout.buildDirectory.file("generated/mod_info.json"))
        }

        val buildMod = project.tasks.register<BuildModTask>("buildMod")
        {
            generatedModInfo.set(
                generateModInfo.flatMap {
                    it.outputFile
                }
            )

            jarFiles.from(modJarFiles)

            modDirectory.set(project.layout.projectDirectory.dir("mod"))

            outputDirectory.set(
                project.layout.buildDirectory.dir(
                    "mod/${modBuilder.getBuild().folderName.get()}"
                )
            )

            dependsOn(generateModInfo)
            dependsOn(modJarTasks)
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

        project.afterEvaluate {
            if (modBuilder.getBuild().copyToGame.get()) {
                buildMod.configure {
                    finalizedBy(installMod)
                }
            }
        }
    }
}
