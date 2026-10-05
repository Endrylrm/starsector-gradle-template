package org.endrylrm.ModBuilder.containers

import org.gradle.api.model.ObjectFactory

import org.endrylrm.ModBuilder.extensions.ModDependencyExtension

class DependencyContainer(private val objects: ObjectFactory)
{
    private val dependencies = mutableListOf<ModDependencyExtension>()

    fun add(configure: ModDependencyExtension.() -> Unit) {
        val dependency = objects.newInstance(ModDependencyExtension::class.java)
        dependency.configure()
        dependencies.add(dependency)
    }

    fun all(): List<ModDependencyExtension> = dependencies.toList()
}
