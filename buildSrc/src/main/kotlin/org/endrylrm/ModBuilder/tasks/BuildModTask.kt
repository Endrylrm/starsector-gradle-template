package org.endrylrm.ModBuilder.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class BuildModTask : DefaultTask()
{
    @get:InputFile
    abstract val generatedModInfo: RegularFileProperty

    @get:InputFile
    abstract val jarFile: RegularFileProperty

    @get:InputDirectory
    abstract val modDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun build()
    {
        project.sync {
            into(outputDirectory)

            // mod_info.json
            from(generatedModInfo) {
                into(".")
            }

            // Mod Resources
            from(modDirectory) {
                into(".")
            }

            // Mod Jar files
            from(jarFile) {
                into("jars")
            }
        }
    }
}
