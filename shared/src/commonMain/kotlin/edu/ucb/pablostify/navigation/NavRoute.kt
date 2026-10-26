package edu.ucb.pablostify.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute {
    @Serializable
    object Login : NavRoute()

    @Serializable
    object Register : NavRoute()

    // Para la realización del examen se creó de antemano una ruta main para que sea más sencilla
    // la navegación entre pantallas
    @Serializable
    object MainList : NavRoute()

    @Serializable
    data class MovieDetail(val movieId: String) : NavRoute()

    @Serializable
    object Profile : NavRoute()

    @Serializable
    object UserInformation : NavRoute()

    // Pestanas de la barra inferior (Firebase Realtime Database y Ajustes)
    @Serializable
    object Notes : NavRoute()

    @Serializable
    object Settings : NavRoute()
}
