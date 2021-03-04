package dev.jimmymorales.xumakero.data.networking

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

interface XumakeroService {
    suspend fun getXumakeros(): List<XumakeroDto>

    suspend fun getClient(xumakeroId: Int): ClientDto

    suspend fun getProject(xumakeroId: Int): ProjectDto
}

class KtorXumakeroService : XumakeroService {
    private val baseUrl = "https://localhost:8080"
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    override suspend fun getXumakeros() =
        client.get<List<XumakeroDto>>(urlString = "$baseUrl/xumakero/all")

    override suspend fun getClient(xumakeroId: Int) =
        client.get<ClientDto>(urlString = "$baseUrl/client/$xumakeroId")

    override suspend fun getProject(xumakeroId: Int) =
        client.get<ProjectDto>(urlString = "$baseUrl/project/$xumakeroId")
}

/**
 *  {
 *      "id": 123,
 *      "name": "Jimmy",
 *      "email": "jim@xumak.com"
 *  }
 *
 */

@Serializable
data class XumakeroDto(
    val id: Int,
    val name: String,
    val email: String,
)

@Serializable
data class ClientDto(
    val id: Int,
    val name: String,
    val contact: String,
    val internal: Boolean,
)

@Serializable
data class ProjectDto(
    val id: Int,
    val name: String,
    val billable: Boolean,
    val xumakeroId: Int,
    val clientId: Int,
)
