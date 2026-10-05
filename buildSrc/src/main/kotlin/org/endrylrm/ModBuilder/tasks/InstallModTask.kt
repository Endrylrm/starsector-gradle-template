package org.endrylrm.ModBuilder.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class InstallModTask : DefaultTask()
{
    @get:InputDirectory
    abstract val modDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val gameModDirectory: DirectoryProperty

    @TaskAction
    fun install()
    {
        project.sync {
            from(modDirectory)
            into(gameModDirectory)
        }
    }
}
