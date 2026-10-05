package org.endrylrm.ModBuilder.model

class ReplaceContainer {
    private val replace = mutableListOf<String>()

    fun add(file: String) {
        replace.add(file)
    }

    fun all(): List<String> = replace
}