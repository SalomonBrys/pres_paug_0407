package fr.sb.pres_paug_0407

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    var slide by remember { mutableStateOf(0) }
    Window(
        onCloseRequest = ::exitApplication,
        title = "PAUG 07 Avril 2026",
        state = rememberWindowState(width = 1920.dp, height = 1080.dp),
        onKeyEvent = { keyEvent ->
            if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionRight) {
                slide = (slide + 1).coerceAtMost(slides.lastIndex)
            } else if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft) {
                slide = (slide - 1).coerceAtLeast(0)
            }
            true
        }
    ) {
        Presentation(slide)
    }
}