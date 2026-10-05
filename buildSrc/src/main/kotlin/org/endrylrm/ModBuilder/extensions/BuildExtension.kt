package org.endrylrm.ModBuilder.extensions

import org.gradle.api.provider.Property

abstract class BuildExtension
{
    abstract val folderName: Property<String>
    abstract val copyToGame: Property<Boolean>
}
