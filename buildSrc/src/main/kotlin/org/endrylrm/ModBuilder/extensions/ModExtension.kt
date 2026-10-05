package org.endrylrm.ModBuilder.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

import org.endrylrm.ModBuilder.model.DependencyContainer
import org.endrylrm.ModBuilder.model.JarContainer
import org.endrylrm.ModBuilder.model.ModDependencyInfo
import org.endrylrm.ModBuilder.model.ReplaceContainer

abstract class ModExtension @Inject constructor(objects: ObjectFactory)
{
    abstract val id: Property<String>
    abstract val name: Property<String>
    abstract val version: Property<String>
    abstract val author: Property<String>
    abstract val utility: Property<Boolean>
    abstract val description: Property<String>
    abstract val gameVersion: Property<String>
    abstract val modPlugin: Property<String>

    abstract val totalConversion: Property<Boolean>

    private val jarContainer = JarContainer()

    fun jars(configure: JarContainer.() -> Unit) {
        jarContainer.configure()
    }

    fun getJars(): List<String> = jarContainer.all()

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

    private val replaceContainer = ReplaceContainer()

    fun replace(configure: ReplaceContainer.() -> Unit) {
        replaceContainer.configure()
    }

    fun getReplace(): List<String> = replaceContainer.all()

    init
    {
        version.convention("0.1.0")
        utility.convention(false)
        gameVersion.convention("0.98a-RC8")
    }
}

