package com.example.testdemoapp.repository

import com.example.testdemoapp.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
	suspend fun <T> calls(call : suspend () -> T) : Resource<T> {
		return withContext(Dispatchers.IO) {
			try {
				Resource.Success(call.invoke())
			}
			catch (e : Throwable) {
				when (e) {
					is HttpException -> {
						Resource.Error(false , "" + e.code() , e.response()?.errorBody())
					}
					else -> {
						Resource.Error(true , "" + e.message , null)
					}
				}
			}
		}
	}
}