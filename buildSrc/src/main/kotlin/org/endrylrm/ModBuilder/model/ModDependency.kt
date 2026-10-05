package org.endrylrm.ModBuilder.model

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class ModDependency @Inject constructor(objects: ObjectFactory)
{
    abstract val id: Property<String>
    abstract val name: Property<String>
    abstract val version: Property<String>
}
