package com.example.myaigenhelper.ui.styles

//import com.example.myaigenhelper.ui.theme.GeminiBlueDark
//import com.example.myaigenhelper.ui.theme.GeminiFucsiaDark
//import com.example.myaigenhelper.ui.theme.GeminiVioletDark
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.myaigenhelper.ui.mesh.meshGradient
import com.example.myaigenhelper.ui.theme.AIColor1
import com.example.myaigenhelper.ui.theme.AIColor2
import com.example.myaigenhelper.ui.theme.AIColor3
import com.example.myaigenhelper.ui.theme.AIColor4
import com.example.myaigenhelper.ui.theme.AIColor5
import com.example.myaigenhelper.ui.theme.AIColor6
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
import com.example.myaigenhelper.ui.theme.Gray700
import com.example.myaigenhelper.ui.theme.Gray800
import com.example.myaigenhelper.ui.theme.Gray900
import com.example.myaigenhelper.ui.theme.Indigo700
import com.example.myaigenhelper.ui.theme.Teal900

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

@Composable
fun Modifier.meshBackgroundColors(
    animatedPoint: Float,
    leftColor: Color,
    middleColor: Color,
    rightColor: Color,
): Modifier {
    return this.meshGradient(
        points = listOf(
            listOf(
                Offset(0f, 0f) to Teal900,
                Offset(.5f, 0f) to Teal900,
                Offset(1f, 0f) to Teal900,
            ),
            listOf(
                Offset(0f, .5f) to Indigo700,
                Offset(.5f, animatedPoint) to Indigo700,
                Offset(1f, .5f) to Indigo700,
            ),
            listOf(
                Offset(0f, 1f) to leftColor,
                Offset(.5f, 1f) to middleColor,
                Offset(1f, 1f) to rightColor,
            ),
        ),
        resolutionX = 32,
        resolutionY = 32,
    )
}

val geminiColorList = listOf<Color>(
    GeminiBlue,
//    GeminiBlueDark,
//    GeminiViolet,
//    GeminiVioletDark,
//    GeminiFucsia,
//    GeminiFucsiaDark,
)
val aiColorList = listOf<Color>(
//    GeminiBlue,
//    GeminiViolet,
//    GeminiFucsia,
    AIColor1,
    AIColor2,
    AIColor3,
    AIColor4,
    AIColor5,
    AIColor6,
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
