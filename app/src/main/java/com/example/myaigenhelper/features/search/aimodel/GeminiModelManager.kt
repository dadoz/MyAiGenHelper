package com.example.myaigenhelper.features.search.aimodel

import com.application.example.myaigenhelper.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

const val GEMINI_MODE_TYPE = "gemini-1.5-flash"

class GeminiModelManager {
    internal val generativeModel by lazy {
        GenerativeModel(
            modelName = GEMINI_MODE_TYPE,
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }
}