package com.example.testdemoapp.network


import com.google.gson.annotations.SerializedName

data class ResponseApi(
    @SerializedName("cid")
    val cid: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<Any>,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)