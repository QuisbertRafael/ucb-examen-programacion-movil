package edu.ucb.pablostify.starwars.data.service

import edu.ucb.pablostify.starwars.data.datasource.StarWarsRemoteDataSource
import edu.ucb.pablostify.starwars.data.dto.PeopleListDto
import edu.ucb.pablostify.starwars.data.mapper.toModel
import edu.ucb.pablostify.starwars.domain.model.Character
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class StarWarsService : StarWarsRemoteDataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override suspend fun fetchData(): Result<List<Character>> {
        return try {
            val response: PeopleListDto = client.get("https://swapi.dev/api/people/").body()
            Result.success(response.results.map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
