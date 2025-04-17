package com.example.myaigenhelper.features.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myaigenhelper.R
import com.example.myaigenhelper.features.search.state.UiState
import com.example.myaigenhelper.features.search.ui.SearchItemView
import com.example.myaigenhelper.features.search.viewmodel.AISearchViewModel
import com.example.myaigenhelper.ui.theme.MyAIGenHelperTheme

@Composable
fun AISearchingScreenView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        aiSearchItemList
            .onEach { item ->
                SearchItemView(
                    imageResource = item.imageResource,
                    title = item.title,
                    description = item.description
                )
            }
    }
}

@Composable
fun SearchInputView(
    aiSearchViewModel: AISearchViewModel = viewModel()
) {
    val uiState by aiSearchViewModel.uiState.collectAsState()
    val placeholderPrompt = stringResource(R.string.prompt_placeholder)
    val placeholderResult = stringResource(R.string.results_placeholder)

    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }
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
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            TextField(
                value = prompt,
                label = { Text(stringResource(R.string.label_prompt)) },
                onValueChange = { prompt = it },
                modifier = Modifier
                    .weight(0.8f)
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically)
            )

            Button(
                onClick = {
//                    val bitmap = BitmapFactory.decodeResource(
//                        context.resources,
//                        images[selectedImage.intValue]
//                    )
                    aiSearchViewModel.sendGeminiPrompt(
//                        bitmap,
                        prompt = prompt
                    )

                    aiSearchViewModel.sendChatGptPrompt(
                        prompt = prompt
                    )
                },
                enabled = prompt.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = stringResource(R.string.action_go))
            }
        }

        when (uiState) {

            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
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


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BakingScreenPreview() {
    MyAIGenHelperTheme {
        AISearchingScreenView()
    }
}