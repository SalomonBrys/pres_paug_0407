package fr.sb.pres_paug_0407

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.min

val slideSize = IntSize(640, 360)

@Composable
fun Presentation() {
    MaterialTheme(
        colorScheme = darkColorScheme(),
    ) {
        val outsideDensity by rememberUpdatedState(LocalDensity.current)
        var insideDensity by remember { mutableStateOf(outsideDensity) }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged {
                    val wRatio = it.width / slideSize.width.toFloat()
                    val hRatio = it.height / slideSize.height.toFloat()
                    val ratio = min(wRatio, hRatio)
                    insideDensity = Density(ratio)
                }
        ) {
            CompositionLocalProvider(
                LocalDensity provides insideDensity
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .size(slideSize.width.dp, slideSize.height.dp)
                            .border(2.dp, Color.White)
                    ) {
                        Text(
                            text = "Compose beyond UI:",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Text(
                            text = "Display and Print.",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }
            }
        }

    }
}