package edu.ucb.pablostify.starwars.data.datasource

import edu.ucb.pablostify.starwars.domain.model.Character

interface StarWarsRemoteDataSource {
    suspend fun fetchData(): Result<List<Character>>
}
