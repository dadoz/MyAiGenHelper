package com.example.myaigenhelper.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitOpenAI: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.x.ai/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val retrofitDeepSeek: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.deepseek.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

