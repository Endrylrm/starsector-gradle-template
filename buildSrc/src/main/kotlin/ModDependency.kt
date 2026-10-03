import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ModDependency(
    val id: String,
    val name: String,
    val version: String
)

fun ModDependency.toJson(): String =
    Json.encodeToString(this)