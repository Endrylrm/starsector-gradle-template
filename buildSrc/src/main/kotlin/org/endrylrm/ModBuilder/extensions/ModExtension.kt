package org.endrylrm.ModBuilder.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

import org.endrylrm.ModBuilder.model.DependencyContainer
import org.endrylrm.ModBuilder.model.JarContainer
import org.endrylrm.ModBuilder.model.ModDependencyInfo

abstract class ModExtension @Inject constructor(objects: ObjectFactory)
{
    abstract val id: Property<String>
    abstract val name: Property<String>
    abstract val version: Property<String>
    abstract val author: Property<String>

    abstract val utility: Property<Boolean>
    abstract val description: Property<String>
    abstract val gameVersion: Property<String>

    private val jarContainer = JarContainer()

    fun jars(configure: JarContainer.() -> Unit) {
        jarContainer.configure()
    }

    fun getJars(): List<String> = jarContainer.all()

    abstract val modPlugin: Property<String>

    private val dependencyContainer = DependencyContainer(objects)

    fun dependencies(configure: DependencyContainer.() -> Unit)
    {
        dependencyContainer.configure()
    }

    fun getDependencies(): List<ModDependencyInfo> =
        dependencyContainer.all().map {
            ModDependencyInfo(
                id = it.id.get(),
                name = it.name.get(),
                version = it.version.orNull
            )
        }

    init
    {
        version.convention("0.1.0")
        utility.convention(false)
        gameVersion.convention("0.98a-RC8")
    }
}

