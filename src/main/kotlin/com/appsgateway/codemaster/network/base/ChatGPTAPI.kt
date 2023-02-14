package com.appsgateway.codemaster.network.base

import com.appsgateway.codemaster.network.model.ApiRequest
import com.appsgateway.codemaster.network.model.ApiResponse
import java.net.URL
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import com.google.gson.Gson

class ChatGPTAPI {

    companion object {
        private val instance: ChatGPTAPI by lazy { ChatGPTAPI() }
        private const val BASE_URL = "https://api.openai.com/v1/";
        private const val API_KEY = "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";


        @JvmName("getInstance1")
        fun getInstance() = instance


    }

    // save your API key here from user settings
    fun saveAPIKey(apiKey: String?) {
        // save API key
    }

    // get API key
    val aPIKey: String
        get() =// get API key
            ""

    suspend fun getResponseFromChatGPT(prompt: String?, operation: OPERATIONTYPE): String {

        var data = ApiRequest(prompt);

        if (operation == OPERATIONTYPE.DOCUMENTATION) {
            data.instruction = "Write a documentation for the following code snippet:$prompt";
        } else if (operation == OPERATIONTYPE.SEARCH_ERROOR) {
            data.instruction = "Write a code snippet to fix the following error:$prompt";
        } else if (operation == OPERATIONTYPE.EXPLAN_THIS_CODE) {
            data.instruction = "explain the following code snippet::$prompt";
        }

        val result = fetchData("edits", data).await();

        return result.choices[0].text;

    }


    fun fetchData(endpoint: String, request: ApiRequest): Deferred<ApiResponse> =
        GlobalScope.async(Dispatchers.IO) {
            val url = URL("$BASE_URL$endpoint")
            val connection = url.openConnection() as HttpURLConnection

            connection.apply {
                requestMethod = "POST"
                doOutput = true
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Authorization", "Bearer $API_KEY")
            }

            val requestJson = Gson().toJson(request)
            connection.outputStream.bufferedWriter().use {
                it.write(requestJson)
            }

            val responseJson = connection.inputStream.bufferedReader().readText()
            Gson().fromJson(responseJson, ApiResponse::class.java)
        }


}