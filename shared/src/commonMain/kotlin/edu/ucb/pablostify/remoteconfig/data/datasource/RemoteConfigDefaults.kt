package edu.ucb.pablostify.remoteconfig.data.datasource

/**
 * Nombres de los parametros. DEBEN coincidir con los de la consola de Firebase
 * (Remote Config > Agregar parametro). Para un parametro nuevo: constante aqui + valor por defecto abajo.
 */
object RemoteConfigKeys {
    const val WELCOME_MESSAGE = "welcome_message"
    const val NOTES_ENABLED = "notes_enabled"
    const val MAINTENANCE_MODE = "maintenance_mode"
}

/** Valores por defecto: se usan si no hay red, si Firebase no esta configurado o si el parametro no existe. */
object RemoteConfigDefaults {
    val values: Map<String, Any> = mapOf(
        RemoteConfigKeys.WELCOME_MESSAGE to "Bienvenido a Pablostify",
        RemoteConfigKeys.NOTES_ENABLED to true,
        RemoteConfigKeys.MAINTENANCE_MODE to false
    )
}
