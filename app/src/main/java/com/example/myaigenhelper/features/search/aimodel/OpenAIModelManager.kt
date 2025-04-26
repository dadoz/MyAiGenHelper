package com.example.myaigenhelper.features.search.aimodel

import com.application.example.myaigenhelper.BuildConfig
import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.chat.completions.ChatCompletionCreateParams

//params
val OPEN_AI_MODEL_TYPE = ChatModel.GPT_4O_2024_05_13

class OpenAIModelManager {
    internal val openAIGenerativeModelClient by lazy {
        try {
            OpenAIOkHttpClient
                .builder()
                .apiKey(apiKey = BuildConfig.OPEN_AI_API_KEY)
                .build()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
//            uiStateMutable.value = UiState.Error(e.localizedMessage ?: "Error Initialize ChatGPT")
            null
        } catch (e: Exception) {
            e.printStackTrace()
//            uiStateMutable.value = UiState.Error(e.localizedMessage ?: "Generic Error ChatGPT")
            null
        }
    }
}