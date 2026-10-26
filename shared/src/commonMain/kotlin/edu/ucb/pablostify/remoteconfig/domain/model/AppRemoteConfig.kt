package edu.ucb.pablostify.remoteconfig.domain.model

/** Valores de Remote Config que usa la app (feature flags + mensaje). */
data class AppRemoteConfig(
    val welcomeMessage: String,
    val notesEnabled: Boolean,
    val maintenanceMode: Boolean
)
