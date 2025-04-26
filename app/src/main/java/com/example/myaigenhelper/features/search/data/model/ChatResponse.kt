package com.example.myaigenhelper.features.search.data.model


data class ChatResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class Message(
    val role: String,
    val content: String
)

data class ChatRequest(
    val messages: List<Message>,
    val model: String,
    val stream: Boolean,
    val temperature: Int
)