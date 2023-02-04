package com.appsgateway.codemaster.network.model


data class ApiRequest(
    var input: String?,
    var instruction: String? = "",
    val model: String? = "text-davinci-edit-001",
    val temperature: Double? = 0.7,
    val top_p: Int? = 1
){
    constructor() : this("","","text-davinci-edit-001",0.7,1)

}