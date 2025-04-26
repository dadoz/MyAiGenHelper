package com.example.myaigenhelper.features.search.aimodel

import com.example.myaigenhelper.features.search.data.services.DeepSeekService
import com.example.myaigenhelper.features.search.data.model.ChatRequest
import com.example.myaigenhelper.features.search.data.model.ChatResponse
import com.example.myaigenhelper.network.retrofitDeepSeek
import retrofit2.Call

const val DEEP_SEEK_MODEL_TYPE = "deepseek-chat"

class DeepSeekModelManager {
    val service: DeepSeekService by lazy {
        retrofitDeepSeek.create(DeepSeekService::class.java)
    }

    fun getChatCompletion(request: ChatRequest): Call<ChatResponse> =
        service.getChatCompletion(request)
}