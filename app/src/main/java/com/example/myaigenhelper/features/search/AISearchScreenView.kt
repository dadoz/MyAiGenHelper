package com.example.myaigenhelper.features.search

import android.content.res.Configuration
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myaigenhelper.features.search.data.AISearch
import com.example.myaigenhelper.features.search.data.AISearchTypeEnum
import com.example.myaigenhelper.features.search.helper.expandedBottomSheetState
import com.example.myaigenhelper.features.search.ui.SearchItemView
import com.example.myaigenhelper.ui.styles.aiColorList
import com.example.myaigenhelper.ui.styles.chatGptColorList
import com.example.myaigenhelper.ui.styles.deepSeekColorList
import com.example.myaigenhelper.ui.styles.geminiColorList
import com.example.myaigenhelper.ui.styles.grokColorList
import com.example.myaigenhelper.ui.styles.meshBackgroundColors
import com.example.myaigenhelper.ui.theme.BlueGray900
import com.example.myaigenhelper.ui.theme.MyAIGenHelperTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AISearchingScreenView() {
    val bottomSheetState = expandedBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val showingModalBottomSheet = remember { mutableStateOf(false) }
    val selectedItem: MutableState<AISearch?> = remember { mutableStateOf(null) }

    val leftColor = remember { Animatable(initialValue = aiColorList[0]) }
    val middleColor = remember { Animatable(initialValue = aiColorList[1]) }
    val rightColor = remember { Animatable(initialValue = aiColorList[2]) }
    LaunchedEffect(Unit) {

        fun animate(color: Animatable<Color, AnimationVector4D>) {
            launch {
                while (true) {
                    color.animateTo(
                        targetValue = aiColorList.random(),
                        animationSpec = tween(
                            durationMillis = Random.nextInt(300, 500)
                        )
                    )
                }
            }
        }
        listOf(leftColor, middleColor, rightColor).map { animate(it) }
    }

    val animatedPoint = remember { Animatable(.8f) }
    LaunchedEffect(Unit) {
        while (true) {
            animatedPoint.animateTo(
                targetValue = .1f,
                animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
            )
            animatedPoint.animateTo(
                targetValue = .1f,
                animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
            )
        }
    }
    Column(
        modifier = Modifier
            .meshBackgroundColors(
                leftColor = leftColor.value,
                middleColor = middleColor.value,
                rightColor = rightColor.value,
                animatedPoint = animatedPoint.value,
            )
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        aiSearchItemList
            .onEach { item ->
                SearchItemView(
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            onClick = {
                                showingModalBottomSheet.value = true
                                selectedItem.value = item
                            }
                        ),
                    imageResource = item.imageResource,
                    title = item.title,
                    description = item.description
                )
            }
    }

    if (showingModalBottomSheet.value) {
        ModalBottomSheetView(
            modifier = Modifier
                .fillMaxSize(),
            bottomSheetState = bottomSheetState,
            onCloseCallback = {
                coroutineScope
                    .launch(context = Dispatchers.Main) {
                        bottomSheetState.hide()
                    }
                showingModalBottomSheet.value = false
            },
            hasStatusBarPaddingHandled = true,
            containerColorList = when (selectedItem.value?.id) {
                AISearchTypeEnum.GEMINI -> geminiColorList

                AISearchTypeEnum.CHATGPT -> chatGptColorList

                AISearchTypeEnum.GROK -> grokColorList

                AISearchTypeEnum.DEEPSEEK -> deepSeekColorList

                else -> listOf(BlueGray900)
            },
            hasDragHandle = true,
        ) {
            AISearchInputView(
                modifier = Modifier,
                selectedItemType = selectedItem.value?.id ?: AISearchTypeEnum.GEMINI,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BakingScreenPreview() {
    MyAIGenHelperTheme {
        AISearchingScreenView()
    }
}