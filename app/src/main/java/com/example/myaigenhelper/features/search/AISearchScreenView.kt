package com.example.myaigenhelper.features.search

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myaigenhelper.features.search.data.AISearch
import com.example.myaigenhelper.features.search.data.AISearchTypeEnum
import com.example.myaigenhelper.features.search.helper.expandedBottomSheetState
import com.example.myaigenhelper.features.search.ui.SearchItemView
import com.example.myaigenhelper.ui.styles.chatGptColorList
import com.example.myaigenhelper.ui.styles.deepSeekColorList
import com.example.myaigenhelper.ui.styles.geminiColorList
import com.example.myaigenhelper.ui.styles.grokColorList
import com.example.myaigenhelper.ui.theme.BlueGray800
import com.example.myaigenhelper.ui.theme.BlueGray900
import com.example.myaigenhelper.ui.theme.DeepPurpleA400
import com.example.myaigenhelper.ui.theme.Gray900
import com.example.myaigenhelper.ui.theme.MyAIGenHelperTheme
import com.example.myaigenhelper.ui.theme.Shadow11
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AISearchingScreenView() {
    val bottomSheetState = expandedBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val showingModalBottomSheet = remember { mutableStateOf(false) }
    val selectedItem: MutableState<AISearch?> = remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
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
                modifier = Modifier
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