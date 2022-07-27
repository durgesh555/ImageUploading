package com.example.testdemoapp.network

import okhttp3.ResponseBody

sealed class Resource<out T> {
	data class Success<out T>(val value : T) : Resource<T>()

	data class Error(
		val isNetworkError : Boolean ,
		val errorCode : String? ,
		val errorBody : ResponseBody?
	) : Resource<Nothing>()
}