package edu.ucb.pablostify.core.settings

/** Modo de tema elegido por el usuario. SYSTEM = seguir el modo oscuro/claro del telefono. */
enum class ThemeMode {
    SYSTEM, LIGHT, DARK;

    companion object {
        fun fromName(name: String?): ThemeMode = entries.firstOrNull { it.name == name } ?: SYSTEM
    }
}
