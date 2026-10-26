package edu.ucb.pablostify.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import edu.ucb.pablostify.designsystem.theme.Spacing

/**
 * Titulo de pantalla. Marcado como "heading" para lectores de pantalla (TalkBack).
 * Si pasas [onBack] (y [backLabel]) aparece un boton "Volver".
 */
@Composable
fun ScreenHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    backLabel: String = ""
) {
    Column(modifier = modifier.fillMaxWidth().padding(bottom = Spacing.md)) {
        if (onBack != null) {
            TextButton(onClick = onBack) { Text("<  $backLabel") }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.semantics { heading() }
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Titulo de seccion dentro de una pantalla. */
@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(top = Spacing.md, bottom = Spacing.xs).semantics { heading() }
    )
}
