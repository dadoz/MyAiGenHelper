package com.example.myaigenhelper.network

import com.example.myaigenhelper.features.search.data.XaiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.x.ai/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val service = retrofit.create(XaiService::class.java)