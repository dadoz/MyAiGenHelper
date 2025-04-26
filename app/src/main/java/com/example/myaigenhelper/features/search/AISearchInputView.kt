package com.example.myaigenhelper.features.search

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.application.example.myaigenhelper.R
import com.example.myaigenhelper.features.search.data.AISearchTypeEnum
import com.example.myaigenhelper.features.search.helper.modalImeAndStatusBarPadding
import com.example.myaigenhelper.features.search.state.UiState
import com.example.myaigenhelper.features.search.viewmodel.AISearchViewModel
import com.example.myaigenhelper.ui.theme.DarkGrayTransparent
import com.example.myaigenhelper.ui.theme.Gray900
import com.example.myaigenhelper.ui.theme.MyAIGenHelperTheme
import com.example.myaigenhelper.ui.theme.White
import kotlin.random.Random

const val densityResizeFactor = .9f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetView(
    modifier: Modifier = Modifier,
    bottomSheetState: SheetState,
    hasStatusBarPaddingHandled: Boolean = true,
    containerColorList: List<Color> = listOf(Color.Blue),
    onCloseCallback: () -> Unit,
    hasDragHandle: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
//    val animationTimestamp = remember { mutableLongStateOf(System.currentTimeMillis()) }
//    val animatedColor by animateColorAsState(
//        targetValue = containerColorList
//            .let { colors ->
//                colors[Random(animationTimestamp.longValue).nextInt(0, colors.size)]
//            },
//        animationSpec = infiniteRepeatable(
//            animation = tween(durationMillis = 1000, easing = EaseInOut),
//            repeatMode = RepeatMode.Reverse
//        )
//    )

//    LaunchedEffect(key1 = animationTimestamp.longValue) {
//        delay(1000)
//        animationTimestamp.longValue = System.currentTimeMillis()
//    }

    ModalBottomSheet(
        modifier = modifier
            .modalImeAndStatusBarPadding(
                hasStatusBarPaddingHandled = hasStatusBarPaddingHandled
            ),
        sheetState = bottomSheetState,
        onDismissRequest = onCloseCallback,
        containerColor = containerColorList[Random(System.currentTimeMillis()).nextInt(
            0,
            containerColorList.size
        )],
        dragHandle = {
            if (hasDragHandle) {
                DragHandle()
            }
        }
    ) {
        //handler for the back button
        BackHandler(
            enabled = bottomSheetState.isVisible,
            onBack = onCloseCallback
        )

        //content of the bottom sheet
        CompositionLocalProvider(
            LocalDensity provides Density(
                density = LocalDensity.current.getResizedDensity()
            )
        ) {
            content()
        }
    }
}

fun Density.getResizedDensity() = when {
    (this.density > 2.5f && this.density < 3.0f) -> this.density
    else -> this.density * densityResizeFactor
}

@Composable
fun AISearchInputView(
    modifier: Modifier = Modifier,
    aiSearchViewModel: AISearchViewModel = viewModel(),
    selectedItemType: AISearchTypeEnum
) {
    val uiState by aiSearchViewModel.uiState.collectAsState()
    val placeholderPrompt = stringResource(R.string.prompt_placeholder)
    val placeholderResult = stringResource(R.string.results_placeholder)

    var prompt by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val errorColor = MaterialTheme.colorScheme.error
    val surfaceColor = MaterialTheme.colorScheme.onSurface
    val textColor by remember(key1 = uiState) {
        mutableStateOf(
            when (uiState) {
                is UiState.Error -> errorColor

                is UiState.Success -> surfaceColor

                else -> surfaceColor
            }
        )
    }
    val result by rememberSaveable(inputs = arrayOf(uiState)) {
        mutableStateOf(
            when (uiState) {
                is UiState.Error -> (uiState as UiState.Error).errorMessage
                is UiState.Success -> (uiState as UiState.Success).outputText
                else -> placeholderResult
            }
        )
    }
//    val icon = remember { FontAwesomeIcons.AllIcons.take(10) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors().copy(
                containerColor = DarkGrayTransparent,
                contentColor = White,
                disabledContainerColor = DarkGrayTransparent,
                disabledContentColor = White,
            ),
            shape = RoundedCornerShape(32.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)

            ) {

                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    fontSize = 26.sp,
                    text = "New Search!"
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    label = {
                        Text(
                            modifier = Modifier,
                            text = placeholderPrompt
                        )
                    },
                    value = prompt,
                    onValueChange = {
                        prompt = it
                    },
                    minLines = 10,
                    colors = OutlinedTextFieldDefaults.colors()
                )
            }
        }

        Button(
            onClick = {
                when (selectedItemType) {
                    AISearchTypeEnum.GEMINI -> {
//                    val bitmap = BitmapFactory.decodeResource(
//                        context.resources,
//                        images[selectedImage.intValue]
//                    )

                        aiSearchViewModel.sendGeminiPrompt(
//                        bitmap,
                            prompt = prompt
                        )
                    }

                    AISearchTypeEnum.CHATGPT ->
                        aiSearchViewModel.sendChatGptPrompt(
                            prompt = prompt
                        )

                    AISearchTypeEnum.GROK ->
                        aiSearchViewModel.sendGrokPrompt(
                            prompt = prompt
                        )

                    AISearchTypeEnum.DEEPSEEK ->
                        aiSearchViewModel.sendDeepSeekPrompt(
                            prompt = prompt
                        )
                }
            },
            colors = ButtonDefaults.buttonColors()
                .copy(
                    containerColor = Gray900,
                    contentColor = White
                ),
            enabled = prompt.isNotEmpty(),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.action_go)
            )
        }

        when (uiState) {
            is UiState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Just having your search",
                        textAlign = TextAlign.Start,
                        color = textColor,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .verticalScroll(scrollState)
                    )

                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            else ->
                Text(
                    text = result,
                    textAlign = TextAlign.Start,
                    color = textColor,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                )
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun AISearchInputViewPreview() {
    MyAIGenHelperTheme {
        AISearchInputView(
            modifier = Modifier,
            selectedItemType = AISearchTypeEnum.GEMINI
        )
    }
}
