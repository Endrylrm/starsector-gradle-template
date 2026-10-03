import org.gradle.api.Project

class ModProperties(private val project: Project)
{
    fun get(name: String): String =
        project.providers.gradleProperty(name).get()

    fun getOptional(name: String): String? =
        project.providers.gradleProperty(name).orNull

    fun getOrDefault(name: String, defaultValue: String): String =
        project.providers.gradleProperty(name)
            .orElse(defaultValue)
            .get()
}
