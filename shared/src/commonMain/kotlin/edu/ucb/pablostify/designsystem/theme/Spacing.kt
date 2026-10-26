package edu.ucb.pablostify.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/** DESIGN SYSTEM - espaciados (usar siempre estos en vez de numeros sueltos). */
object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
}

/** DESIGN SYSTEM - tamanos de usabilidad. 48dp es el minimo recomendado para zonas tactiles. */
object Sizes {
    val minTouchTarget = 48.dp
}

/** DESIGN SYSTEM - formas (esquinas redondeadas). */
val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(20.dp)
)
