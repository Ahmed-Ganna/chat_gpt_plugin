package com.appsgateway.codemaster.network.model

data class ApiResponse(
    val choices: List<Choice>,
    val created: Int,
    val `object`: String,
    val usage: Usage
)