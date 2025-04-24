package com.example.myaigenhelper.ui.styles

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.myaigenhelper.ui.theme.BlueGray500
import com.example.myaigenhelper.ui.theme.BlueGray700
import com.example.myaigenhelper.ui.theme.BlueGray800
import com.example.myaigenhelper.ui.theme.BlueGray900
import com.example.myaigenhelper.ui.theme.DeepSeek1
import com.example.myaigenhelper.ui.theme.DeepSeek2
import com.example.myaigenhelper.ui.theme.DeepSeek3
import com.example.myaigenhelper.ui.theme.DeepSeek4
import com.example.myaigenhelper.ui.theme.DeepSeek5
import com.example.myaigenhelper.ui.theme.GeminiBlue
import com.example.myaigenhelper.ui.theme.GeminiBlueDark
import com.example.myaigenhelper.ui.theme.GeminiFucsia
import com.example.myaigenhelper.ui.theme.GeminiFucsiaDark
import com.example.myaigenhelper.ui.theme.GeminiViolet
import com.example.myaigenhelper.ui.theme.GeminiVioletDark
import com.example.myaigenhelper.ui.theme.Gray500
import com.example.myaigenhelper.ui.theme.Gray700
import com.example.myaigenhelper.ui.theme.Gray800
import com.example.myaigenhelper.ui.theme.Gray900

@Composable
fun angleCircleAnimated(): State<Float> {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    return infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(1500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
        label = ""
    )
}

@Composable
fun multicolorBackgroundAnimated(colors: List<Color>): Brush {
    return Brush.sweepGradient(colors)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun multicolorBackgroundAnimatedBottomSheet(colors: List<Color>): Color {
    return Color.Yellow
}

val geminiColorList = listOf<Color>(
    GeminiBlue,
    GeminiBlueDark,
    GeminiViolet,
    GeminiVioletDark,
    GeminiFucsia,
    GeminiFucsiaDark
)
val grokColorList = listOf<Color>(
    Gray900,
    Gray800,
    Gray700
)
val chatGptColorList = listOf<Color>(
    BlueGray700,
    BlueGray800,
    BlueGray900
)
val deepSeekColorList = listOf<Color>(
    DeepSeek1,
    DeepSeek2,
    DeepSeek3,
    DeepSeek4,
    DeepSeek5,
)
