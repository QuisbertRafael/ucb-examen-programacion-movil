package edu.ucb.pablostify.moviedetail.data

import edu.ucb.pablostify.moviedetail.domain.model.CastMember
import edu.ucb.pablostify.moviedetail.domain.model.MovieDetail
import edu.ucb.pablostify.moviedetail.domain.repository.MovieDetailRepository
import edu.ucb.pablostify.moviedetail.domain.valueobject.Rating

class MovieDetailRepositoryImpl : MovieDetailRepository {
    private val details = mapOf(
        "matrix" to MovieDetail(
            "matrix", "The Matrix",
            "Un programador descubre que la realidad que conoce es una simulación controlada por máquinas.",
            "", listOf(CastMember("1", "Keanu Reeves", ""), CastMember("2", "Laurence Fishburne", ""), CastMember("3", "Carrie-Anne Moss", "")), Rating(8.7f)
        ),
        "spiderman" to MovieDetail(
            "spiderman", "Spider-Man",
            "Un joven adquiere poderes arácnidos y debe aprender a usarlos para proteger su ciudad.",
            "", listOf(CastMember("4", "Tom Holland", ""), CastMember("5", "Zendaya", "")), Rating(7.9f)
        ),
        "interstellar" to MovieDetail(
            "interstellar", "Interstellar",
            "Un grupo de exploradores viaja a través de un agujero de gusano para buscar un nuevo hogar para la humanidad.",
            "", listOf(CastMember("6", "Matthew McConaughey", ""), CastMember("7", "Anne Hathaway", ""), CastMember("8", "Jessica Chastain", "")), Rating(8.0f)
        )
    )

    override suspend fun getMovieDetail(movieId: String): Result<MovieDetail> = details[movieId]?.let { Result.success(it) }
        ?: Result.failure(NoSuchElementException("Película no encontrada: $movieId"))

    override suspend fun submitReview(movieId: String, review: String, rating: Float): Result<Unit> {
        if (details[movieId] == null) return Result.failure(NoSuchElementException("Película no encontrada"))
        if (review.isBlank()) return Result.failure(IllegalArgumentException("La reseña no puede estar vacía"))
        return try { Rating(rating); Result.success(Unit) } catch (e: IllegalArgumentException) { Result.failure(e) }
    }
}
