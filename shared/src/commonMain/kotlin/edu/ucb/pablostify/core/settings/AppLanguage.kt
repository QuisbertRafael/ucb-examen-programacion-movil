package edu.ucb.pablostify.core.settings

/**
 * Idioma de la app. [tag] es el codigo de idioma (es, en). SYSTEM ("") = idioma del telefono.
 * Para agregar un idioma: nuevo valor aqui + carpeta composeResources/values-<codigo>/strings.xml.
 */
enum class AppLanguage(val tag: String) {
    SYSTEM(""),
    SPANISH("es"),
    ENGLISH("en");

    companion object {
        fun fromTag(tag: String?): AppLanguage = entries.firstOrNull { it.tag == tag } ?: SYSTEM
    }
}
