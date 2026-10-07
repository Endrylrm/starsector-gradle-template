package org.endrylrm.ModBuilder.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class BuildModTask : DefaultTask()
{
    @get:Inject
    protected abstract val fileSystemOperations: FileSystemOperations

    @get:InputFile
    abstract val generatedModInfo: RegularFileProperty

    @get:InputFiles
    abstract val jarFiles: ConfigurableFileCollection

    @get:InputDirectory
    abstract val modDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun build()
    {
        fileSystemOperations.sync {
            into(outputDirectory)

            // mod_info.json
            from(generatedModInfo) {
                into(".")
            }

            // Mod Resources
            from(modDirectory) {
                exclude("**/readme.txt")
                into(".")
            }

            // Mod Jar files
            from(jarFiles) {
                into("jars")
            }

            println("mod built in: ${outputDirectory.get().asFile}...")
        }
    }
}
