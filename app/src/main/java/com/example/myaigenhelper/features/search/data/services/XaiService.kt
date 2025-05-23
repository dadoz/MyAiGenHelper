package com.example.myaigenhelper.features.search.data.services

import com.application.example.myaigenhelper.BuildConfig
import com.example.myaigenhelper.features.search.data.model.ChatRequest
import com.example.myaigenhelper.features.search.data.model.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface XaiService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${BuildConfig.GROK_API_KEY}"
    )
    @POST("v1/chat/completions")
    fun getChatCompletion(
        @Body request: ChatRequest
    ): Call<ChatResponse>
}
