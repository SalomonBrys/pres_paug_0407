package fr.sb.pres_paug_0407

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.use
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jetbrains.skia.EncodedImageFormat
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeBytes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

fun main() = application {
    var slide by remember { mutableStateOf(0) }
    var export by remember { mutableStateOf(false) }
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
        MenuBar {
            Menu("Export") {
                Item(
                    text = "PNGs",
                ) {
                    export = true
                }
            }
        }
        Presentation(slide)

        if (export) {
            DialogWindow(
                onCloseRequest = {},
                title = "Export",
            ) {
                var exporting by remember { mutableStateOf(true) }
                LaunchedEffect(Unit) {
                    withContext(Dispatchers.Default) {
                        exportPngs()
                        exporting = false
                        delay(1.seconds)
                        export = false
                    }
                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Exporting PNGs...",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        if (exporting) {
                            CircularProgressIndicator()
                        } else {
                            Text(
                                text = "Done!",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
fun exportPngs() {
    val dir = Path("PNGs")
    dir.createDirectories()
    slides.forEachIndexed { index, slide ->
        ImageComposeScene(
            width = slideSize.width * 2,
            height = slideSize.height * 2,
            density = Density(2f)
        ) {
            MaterialTheme(
                colorScheme = darkColorScheme(),
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .size(slideSize.width.dp, slideSize.height.dp)
                    ) {
                        slide()
                    }
                }
            }
        }.use { scene ->
            val img = scene.render(1.seconds)
            val data = img.encodeToData(EncodedImageFormat.PNG)!!
            dir.resolve("slide-${index + 1}.png").writeBytes(data.bytes)
        }
    }
}