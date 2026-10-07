package org.endrylrm.ModBuilder.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class InstallModTask : DefaultTask()
{
    @get:Inject
    protected abstract val fileSystemOperations: FileSystemOperations

    @get:InputDirectory
    abstract val modDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val gameModDirectory: DirectoryProperty

    @TaskAction
    fun install()
    {
        fileSystemOperations.sync {
            from(modDirectory)
            into(gameModDirectory)
            println("installed in: ${gameModDirectory.get().asFile}...")
        }
    }
}
