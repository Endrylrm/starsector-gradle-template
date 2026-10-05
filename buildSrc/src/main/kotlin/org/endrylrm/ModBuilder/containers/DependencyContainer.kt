package org.endrylrm.ModBuilder.containers

import org.endrylrm.ModBuilder.model.ModDependency
import org.gradle.api.model.ObjectFactory

class DependencyContainer(private val objects: ObjectFactory)
{
    private val dependencies = mutableListOf<ModDependency>()

    fun add(configure: ModDependency.() -> Unit) {
        val dependency = objects.newInstance(ModDependency::class.java)
        dependency.configure()
        dependencies.add(dependency)
    }

    fun all(): List<ModDependency> = dependencies.toList()
}
