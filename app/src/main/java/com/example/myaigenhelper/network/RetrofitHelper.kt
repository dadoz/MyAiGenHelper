package com.example.myaigenhelper.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val OPEN_AI_ENDPOINT = "https://api.x.ai/"
const val DEEP_SEEK_ENDPOINT = "https://api.deepseek.com/"

//this should be handled with an Hilt injection implementation with more baseURL -
//now for simplicity I will handle with two different implementation
val retrofitOpenAI: Retrofit = Retrofit.Builder()
    .baseUrl(OPEN_AI_ENDPOINT)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val retrofitDeepSeek: Retrofit = Retrofit.Builder()
    .baseUrl(DEEP_SEEK_ENDPOINT)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
