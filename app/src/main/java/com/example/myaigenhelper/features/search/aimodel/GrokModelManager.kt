package com.example.myaigenhelper.features.search.aimodel

import com.example.myaigenhelper.features.search.data.XaiService
import com.example.myaigenhelper.features.search.data.model.ChatRequest
import com.example.myaigenhelper.features.search.data.model.ChatResponse
import com.example.myaigenhelper.network.retrofitOpenAI
import retrofit2.Call

const val GROK_MODEL_TYPE = "grok-3-latest"
const val GROK_USER_TYPE = "user"

class GrokModelManager {
    val service: XaiService by lazy {
        retrofitOpenAI.create(XaiService::class.java)
    }

    fun getChatCompletion(request: ChatRequest): Call<ChatResponse> =
        service.getChatCompletion(request)
}