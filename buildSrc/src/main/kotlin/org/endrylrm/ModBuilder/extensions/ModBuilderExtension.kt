package org.endrylrm.ModBuilder.extensions

import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class ModBuilderExtension @Inject constructor(objects: ObjectFactory)
{
    private val modExtension = objects.newInstance(ModExtension::class.java)
    private val buildExtension = objects.newInstance(BuildExtension::class.java)

    fun mod(configure: ModExtension.() -> Unit)
    {
        modExtension.configure()
    }

    fun build(configure: BuildExtension.() -> Unit)
    {
        buildExtension.configure()
    }

    fun getMod(): ModExtension = modExtension
    fun getBuild(): BuildExtension = buildExtension

    init
    {
        buildExtension.folderName.convention(modExtension.id)
        buildExtension.copyToGame.convention(false)
    }
}

