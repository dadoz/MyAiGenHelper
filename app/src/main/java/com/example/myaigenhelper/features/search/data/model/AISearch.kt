package com.example.myaigenhelper.features.search.data.model

import androidx.annotation.Keep

@Keep
data class AISearch(
    val id: AISearchTypeEnum,
    val imageResource: Int,
    val title: String,
    val description: String
)

@Keep
enum class AISearchTypeEnum(val value: String) {
    GEMINI(value = "GEMINI"),
    CHATGPT(value = "CHATGPT"),
    GROK(value = "GROK"),
    DEEPSEEK(value = "DEEPSEEK")
}