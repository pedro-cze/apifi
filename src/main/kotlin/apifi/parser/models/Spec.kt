package apifi.parser.models

import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.security.SecurityScheme

data class CommonSpec(val models: List<Model>, val securityDefinitions: List<SecurityDefinition>)

data class Spec(val paths: List<Path>, val models: List<Model>, val securityRequirements: List<String>)

data class Path(val url: String, val operations: List<Operation>?)

data class Operation(
        val type: PathItem.HttpMethod,
        val params: List<Param>?,
        val requestBodyType: String?,
        val response: List<String>?,
        val securitySchemeType: SecurityDefinitionType = SecurityDefinitionType.BASIC_AUTH
)

data class Param(
        val name: String,
        val dataType: String,
        val isRequired: Boolean,
        val type: ParamType
)

enum class ParamType {
    Query, Path, Header;

    companion object {
        fun fromString(typeInString: String): ParamType =
                when (typeInString) {
                    "path" -> Path
                    "query" -> Query
                    "header" -> Header
                    else -> error("Invalid param type")

                }
    }
}

data class SecurityDefinition(val name: String, val type: SecurityDefinitionType)

enum class SecurityDefinitionType {
    BASIC_AUTH;

    companion object {
        fun fromTypeAndScheme(type: SecurityScheme.Type, scheme: String) =
                when {
                    type == SecurityScheme.Type.HTTP && scheme == "basic" -> BASIC_AUTH
                    else -> error("Security scheme not supported yet")
                }
    }
}