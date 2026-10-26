package edu.ucb.pablostify.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * DESIGN SYSTEM - colores.
 * Todos los colores de la app salen de aqui. En las pantallas NUNCA se usa Color(0xFF...),
 * siempre MaterialTheme.colorScheme.xxx, asi el modo oscuro funciona solo.
 */
val LightColors = lightColorScheme(
    primary = Color(0xFF0F7B3F),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB7F2C8),
    onPrimaryContainer = Color(0xFF00210D),
    secondary = Color(0xFF4F6354),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD2E8D5),
    onSecondaryContainer = Color(0xFF0D1F13),
    background = Color(0xFFF7FBF5),
    onBackground = Color(0xFF181D19),
    surface = Color(0xFFF7FBF5),
    onSurface = Color(0xFF181D19),
    surfaceVariant = Color(0xFFDCE5DC),
    onSurfaceVariant = Color(0xFF404942),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    outline = Color(0xFF707971)
)

val DarkColors = darkColorScheme(
    primary = Color(0xFF6CDB8E),
    onPrimary = Color(0xFF00391B),
    primaryContainer = Color(0xFF00522A),
    onPrimaryContainer = Color(0xFF8AF8A8),
    secondary = Color(0xFFB6CCB9),
    onSecondary = Color(0xFF213527),
    secondaryContainer = Color(0xFF384B3D),
    onSecondaryContainer = Color(0xFFD2E8D5),
    background = Color(0xFF101411),
    onBackground = Color(0xFFE0E4DE),
    surface = Color(0xFF101411),
    onSurface = Color(0xFFE0E4DE),
    surfaceVariant = Color(0xFF404942),
    onSurfaceVariant = Color(0xFFC0C9C0),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    outline = Color(0xFF8A938A)
)
