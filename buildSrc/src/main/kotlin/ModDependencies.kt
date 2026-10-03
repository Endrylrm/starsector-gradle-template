import org.gradle.api.Project

fun Project.getModDependencies(): List<ModDependency>
{
    val properties = ModProperties(this)

    val dependencies = mutableListOf<ModDependency>()
    var index = 1

    while (true)
    {
        val prefix = "modDependency.$index"

        val id = properties.getOptional("$prefix.id")
            ?: break

        val name = properties.get("$prefix.name")
        val version = properties.get("$prefix.version")

        dependencies += ModDependency(
            id = id,
            name = name,
            version = version
        )

        index++
    }

    return dependencies
}
