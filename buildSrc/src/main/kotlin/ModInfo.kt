import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ModInfo(
    val id: String,
    val name: String,
    val version: String,
    val author: String,
    val utility: Boolean,
    val description: String,
    val plugin: String,
    val gameVersion: String,
    val jars: List<String>,
    val dependencies: List<ModDependency>
)

private val json = Json {
    prettyPrint = true
}

fun ModInfo.toJson(): String =
    json.encodeToString(this)