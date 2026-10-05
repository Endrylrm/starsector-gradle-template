package org.endrylrm.ModBuilder.containers

class JarContainer {
    private val jars = mutableListOf<String>()

    fun add(jar: String) {
        jars.add(jar)
    }

    fun all(): List<String> = jars
}
