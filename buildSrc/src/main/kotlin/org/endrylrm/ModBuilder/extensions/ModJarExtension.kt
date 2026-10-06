package org.endrylrm.ModBuilder.extensions

import org.gradle.api.Named
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class ModJarExtension @Inject constructor(objects: ObjectFactory) : Named
{
    abstract val outputFilename: Property<String>
    abstract val sourceProject: Property<String>

    fun from(project: Project)
    {
        sourceProject.set(project.path)
    }
}
