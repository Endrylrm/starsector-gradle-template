package org.endrylrm.ModBuilder.model

import kotlinx.serialization.Serializable

@Serializable
data class ModInfo(
    val id: String,
    val name: String,
    val version: String,
    val author: String,
    val utility: String,
    val description: String,
    val gameVersion: String,
    val modPlugin: String,
    val totalConversion: String? = null,
    val jars: List<String>,
    val dependencies: List<ModDependencyInfo>? = null,
    val replace: List<String>? = null,
)

@Serializable
data class ModDependencyInfo(
    val id: String,
    val name: String,
    val version: String? = null
)
