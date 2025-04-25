package com.example.myaigenhelper.features.search.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.example.myaigenhelper.BuildConfig
import com.example.myaigenhelper.features.search.state.UiState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.chat.completions.ChatCompletionCreateParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.jvm.optionals.getOrNull

class AISearchViewModel : ViewModel() {
    private val uiStateMutable: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        uiStateMutable.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun sendGeminiPrompt(
        bitmap: Bitmap? = null,
        prompt: String
    ) {
        uiStateMutable.value = UiState.Loading

        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        bitmap?.let {
                            image(bitmap)
                        }
                        text(prompt)
                    }
                )
                response.text
                    ?.let { outputContent ->
                        uiStateMutable.value = UiState.Success(outputContent)
                    }
            } catch (e: Exception) {
                uiStateMutable.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }

    // Configures using the `OPENAI_API_KEY`, `OPENAI_ORG_ID` and `OPENAI_PROJECT_ID`
    // credential` is required
    // environment variables
    fun sendGrokPrompt(prompt: String) {
        uiStateMutable.value = UiState.Success("NOT IMPLEMENTED")
    }

    fun sendDeepSeekPrompt(prompt: String) {
        uiStateMutable.value = UiState.Success("NOT IMPLEMENTED")
    }

    fun sendChatGptPrompt(prompt: String) {
        try {
            val client = OpenAIOkHttpClient
                .fromEnv()
            viewModelScope.launch(context = Dispatchers.IO) {
                //params
                val params = ChatCompletionCreateParams.builder()
                    .addUserMessage(prompt)
                    .model(ChatModel.GPT_4O_2024_05_13)
                    .build()
                //ask to chatgpt and take first chioice
                client.chat()
                    .completions()
                    .create(params)
                    .choices()[0]
                    .message()
                    .content()
                    .getOrNull()
                    ?.takeIf { it.isNotEmpty() }
                    ?.let { outputContent ->
                        uiStateMutable.value = UiState.Success(outputContent)
                    }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            uiStateMutable.value = UiState.Error(e.localizedMessage ?: "ERROR ChatGPT")
        }
    }
}