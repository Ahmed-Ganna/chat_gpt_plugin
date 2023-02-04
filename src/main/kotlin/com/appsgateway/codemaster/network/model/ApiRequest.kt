package com.appsgateway.codemaster.network.model


data class ApiRequest(
    var input: String?,
    var instruction: String? = "",
    var prompt: String? = "",
    val model: String? = "text-davinci-002",
    val temperature: Double? = 0.5,
    val max_tokens: Int = 100,
    val top_p: Int? = 1
) {

}