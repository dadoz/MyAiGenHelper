package com.example.myaigenhelper.features.search.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myaigenhelper.features.search.aimodel.DEEP_SEEK_MODEL_TYPE
import com.example.myaigenhelper.features.search.aimodel.DeepSeekModelManager
import com.example.myaigenhelper.features.search.aimodel.GROK_MODEL_TYPE
import com.example.myaigenhelper.features.search.aimodel.GROK_USER_TYPE
import com.example.myaigenhelper.features.search.aimodel.GeminiModelManager
import com.example.myaigenhelper.features.search.aimodel.GrokModelManager
import com.example.myaigenhelper.features.search.aimodel.OPEN_AI_MODEL_TYPE
import com.example.myaigenhelper.features.search.aimodel.OpenAIModelManager
import com.example.myaigenhelper.features.search.data.model.ChatRequest
import com.example.myaigenhelper.features.search.data.model.ChatResponse
import com.example.myaigenhelper.features.search.data.model.Message
import com.example.myaigenhelper.features.search.state.UiState
import com.google.ai.client.generativeai.type.content
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.openai.errors.RateLimitException
import com.openai.models.chat.completions.ChatCompletionCreateParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.jvm.optionals.getOrNull

class AISearchViewModel : ViewModel() {
    private val uiStateMutable: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        uiStateMutable.asStateFlow()

    //model managers
    private val geminiModelManager = GeminiModelManager()
    private val openAIGenerativeModelManager = OpenAIModelManager()
    private val grokModelManager = GrokModelManager()
    private val deepSeekModelManager = DeepSeekModelManager()

    fun sendGeminiPrompt(
        bitmap: Bitmap? = null,
        prompt: String
    ) {
        uiStateMutable.value = UiState.Loading

        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                val response = geminiModelManager.generativeModel
                    .generateContent(
                        content {
                            bitmap?.let {
                                image(bitmap)
                            }
                            text(prompt)
                        }
                    )
                response.text
                    ?.let { outputContent ->
                        uiStateMutable.value = UiState.Success(outputContent)
                    }
            } catch (e: Exception) {
                uiStateMutable.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }

    /**
     * GROK prompt
     * Configures using the `OPENAI_API_KEY`, `OPENAI_ORG_ID` and `OPENAI_PROJECT_ID`
     * credential` is required
     * environment variables
     */
    fun sendGrokPrompt(prompt: String) {
        //set loading state
        uiStateMutable.value = UiState.Loading

        //build request type
        val request = ChatRequest(
            messages = listOf(
                Message(GROK_USER_TYPE, prompt)
            ),
            model = GROK_MODEL_TYPE,
            stream = false,
            temperature = 0
        )
        viewModelScope.launch(context = Dispatchers.IO) {
            //send request
            grokModelManager.getChatCompletion(request = request)
                .enqueue(object : Callback<ChatResponse> {
                    override fun onResponse(
                        request: Call<ChatResponse?>,
                        response: Response<ChatResponse?>
                    ) {
                        if (response.isSuccessful) {
                            val reply = response.body()
                            //set message to ui
                            uiStateMutable.value = UiState.Success(
                                reply?.choices?.firstOrNull()?.message?.content ?: ""
                            )
                        } else {
                            //parse error message
                            try {
                                (response.errorBody()
                                    ?.string()
                                    .also {
                                        Timber.e("Error: $it")
                                    }
                                    ?.parseGrokToPairObj()
                                    ?.second ?: "GROK ERROR generic")
                                    .also {
                                        uiStateMutable.value = UiState.Error(
                                            errorMessage = it
                                        )
                                    }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                uiStateMutable.value = UiState.Error(
                                    errorMessage =
                                        "GROK ERROR generic"
                                )
                            }
                        }
                    }

                    override fun onFailure(
                        request: Call<ChatResponse?>,
                        response: Throwable
                    ) {
                        response.printStackTrace()
                        uiStateMutable.value =
                            UiState.Error(response.localizedMessage ?: "GROK ERROR generic")
                    }
                })
        }
    }

    /**
     * DeepSeek prompt
     *
     */
    fun sendDeepSeekPrompt(prompt: String) {
        //set loading state
        uiStateMutable.value = UiState.Loading

        //build request type
        val request = ChatRequest(
            model = DEEP_SEEK_MODEL_TYPE,
            messages = listOf(
                Message(GROK_USER_TYPE, prompt)
            ),
            stream = false,
            temperature = 0
        )
        viewModelScope.launch(context = Dispatchers.IO) {
            //send request
            deepSeekModelManager.getChatCompletion(request = request)
                .enqueue(object : Callback<ChatResponse> {
                    override fun onResponse(
                        request: Call<ChatResponse?>,
                        response: Response<ChatResponse?>
                    ) {
                        if (response.isSuccessful) {
                            val reply = response.body()
                            //set message to ui
                            uiStateMutable.value = UiState.Success(
                                reply?.choices?.firstOrNull()?.message?.content ?: ""
                            )
                        } else {
                            //parse error message
                            try {
                                (response.errorBody()
                                    ?.string()
                                    .also {
                                        Timber.e("Error: $it")
                                    }
                                    ?.parseDeepSeekToPairObj()
                                    ?.second ?: "DeepSeek generic")
                                    .also {
                                        uiStateMutable.value = UiState.Error(
                                            errorMessage = it
                                        )
                                    }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                uiStateMutable.value = UiState.Error(
                                    errorMessage =
                                        "DeepSeek generic"
                                )
                            }
                        }
                    }

                    override fun onFailure(
                        request: Call<ChatResponse?>,
                        response: Throwable
                    ) {
                        response.printStackTrace()
                        uiStateMutable.value =
                            UiState.Error(response.localizedMessage ?: "GROK ERROR generic")
                    }
                })
        }
    }

    fun sendChatGptPrompt(prompt: String) {
        uiStateMutable.value = UiState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                //params
                val params = ChatCompletionCreateParams.builder()
                    .addUserMessage(prompt)
                    .model(OPEN_AI_MODEL_TYPE)
                    .build()
                //ask to chatgpt and take first choice
                openAIGenerativeModelManager.openAIGenerativeModelClient
                    ?.let {
                        it.chat()
                            .completions()
                            .create(params)
                            .choices()[0]
                            .message()
                            .content()
                            .getOrNull()
                            ?.takeIf { it.isNotEmpty() }
                            ?.let { outputContent ->
                                uiStateMutable.value = UiState.Success(outputContent)
                            }
                    }
                    ?: run {
                        throw Exception("CHATGPT - No More search")
                    }
            } catch (e: RateLimitException) {
                e.printStackTrace()
                uiStateMutable.value = UiState.Error(e.localizedMessage ?: "No More search")
            } catch (e: Exception) {
                e.printStackTrace()
                uiStateMutable.value =
                    UiState.Error(e.localizedMessage ?: "Generic Error ChatGPT")
            }
        }
    }
}

//move to extension
@Throws(JsonSyntaxException::class, IllegalStateException::class)
fun String?.parseGrokToPairObj(): Pair<String?, String?> {
    val obj: JsonObject = JsonParser.parseString(this).asJsonObject
    val code: String? = obj.get("code").asString
    val error: String? = obj.get("error").asString
    return Pair(first = code, second = error)
}

@Throws(JsonSyntaxException::class, IllegalStateException::class)
fun String?.parseDeepSeekToPairObj(): Pair<String?, String?> {
    val obj: JsonObject = JsonParser.parseString(this).asJsonObject.get("error").asJsonObject
    val code: String? = obj.get("code").asString
    val error: String? = obj.get("message").asString
    return Pair(first = code, second = error)
}