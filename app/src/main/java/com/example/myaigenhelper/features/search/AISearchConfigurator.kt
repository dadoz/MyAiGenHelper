package com.example.myaigenhelper.features.search

import com.application.example.myaigenhelper.R
import com.example.myaigenhelper.features.search.data.AISearch
import com.example.myaigenhelper.features.search.data.AISearchTypeEnum

val aiSearchItemList = listOf(
    AISearch(
        id = AISearchTypeEnum.GEMINI,
        imageResource = R.drawable.ic_gemini,
        title = "Choice Gemini",
        description = "to handle just choice the task"
    ),
    AISearch(
        id = AISearchTypeEnum.CHATGPT,
        imageResource = R.drawable.ic_chatgpt,
        title = "Choice ChatGPT",
        description = "to handle just choice the task"
    ),
    AISearch(
        id = AISearchTypeEnum.GROK,
        imageResource = R.drawable.ic_grok,
        title = "Choice Grok",
        description = "to handle just choice the task"
    ),
    AISearch(
        id = AISearchTypeEnum.DEEPSEEK,
        imageResource = R.drawable.ic_deepseek,
        title = "Choice DeepSeek",
        description = "to handle just choice the task"
    )
)