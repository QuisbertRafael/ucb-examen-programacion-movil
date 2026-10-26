package edu.ucb.pablostify.starwars.data.repository

import edu.ucb.pablostify.starwars.data.datasource.StarWarsRemoteDataSource
import edu.ucb.pablostify.starwars.domain.model.Character
import edu.ucb.pablostify.starwars.domain.repository.StarWarsRepository

class StarWarsRepositoryImpl(private val dataSource: StarWarsRemoteDataSource) : StarWarsRepository {
    override suspend fun getCharacters(): Result<List<Character>> {
        return dataSource.fetchData()
    }
}
