package fr.sb.pres_paug_0407

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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

val slides: List<@Composable ColumnScope.() -> Unit> = listOf(
    { Slide1() },
    { Slide2() },
    { Slide3() },
)

@Composable
fun Presentation(slide: Int) {
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
                        slides[slide]()
                    }
                }
            }
        }

    }
}

@Composable
fun ColumnScope.Slide1() {
    Text(
        text = "Compose beyond UI:",
        style = MaterialTheme.typography.headlineMedium,
    )
    Text(
        text = "Display and Print.",
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
fun ColumnScope.Slide2() {
    Text(
        text = "Second slide!",
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
fun ColumnScope.Slide3() {
    Text(
        text = "https://github.com/SalomonBrys/pres_paug_0407",
        style = MaterialTheme.typography.bodyLarge,
    )
    Text(
        text = "😍",
        style = MaterialTheme.typography.bodyLarge,
    )
}
