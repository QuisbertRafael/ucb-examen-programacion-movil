package edu.ucb.pablostify.starwars.domain.usecase

import edu.ucb.pablostify.starwars.domain.model.Character
import edu.ucb.pablostify.starwars.domain.repository.StarWarsRepository

class GetStarWarsCharactersUseCase(private val repository: StarWarsRepository) {
    suspend operator fun invoke(): Result<List<Character>> = repository.getCharacters()
}
