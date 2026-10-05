package org.endrylrm.ModBuilder.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class ModDependencyExtension @Inject constructor(objects: ObjectFactory)
{
    abstract val id: Property<String>
    abstract val name: Property<String>
    abstract val version: Property<String>
}
