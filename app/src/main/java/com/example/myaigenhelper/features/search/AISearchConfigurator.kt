package com.example.myaigenhelper.features.search

import com.application.example.myaigenhelper.R
import com.example.myaigenhelper.features.search.data.model.AISearch
import com.example.myaigenhelper.features.search.data.model.AISearchTypeEnum

val aiSearchItemList = listOf(
    AISearch(
        id = AISearchTypeEnum.GEMINI,
        imageResource = R.drawable.ic_gemini,
        title = "Gemini",
        description = "to handle just choice the task"
    ),
    AISearch(
        id = AISearchTypeEnum.CHATGPT,
        imageResource = R.drawable.ic_chatgpt,
        title = "ChatGPT",
        description = "to handle just choice the task"
    ),
    AISearch(
        id = AISearchTypeEnum.GROK,
        imageResource = R.drawable.ic_grok,
        title = "Grok",
        description = "to handle just choice the task"
    ),
    AISearch(
        id = AISearchTypeEnum.DEEPSEEK,
        imageResource = R.drawable.ic_deepseek,
        title = "DeepSeek",
        description = "to handle just choice the task"
    ),
    AISearch(
        id = AISearchTypeEnum.LLAMA,
        imageResource = R.drawable.ic_meta,
        title = "Llama Meta AI",
        description = "to handle just choice the task"
    )
)