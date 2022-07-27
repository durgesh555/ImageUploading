package com.example.testdemoapp.network

import com.example.testdemoapp.BuildConfig
import com.example.testdemoapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {


	fun retrofitInstance() : ApiInterface {

		val level = if (BuildConfig.DEBUG) {
			HttpLoggingInterceptor.Level.HEADERS
		}
		else {
			HttpLoggingInterceptor.Level.NONE
		}

		val okHttpClient = OkHttpClient.Builder().apply {
			connectTimeout(30 , TimeUnit.SECONDS)
			readTimeout(30 , TimeUnit.SECONDS)
			writeTimeout(30 , TimeUnit.SECONDS)
			addInterceptor(HttpLoggingInterceptor().setLevel(level))
		}.build()

		return Retrofit.Builder().apply {
			baseUrl(Constants.BASE_URL)
			client(okHttpClient)
			addConverterFactory(GsonConverterFactory.create())
		}.build().create(ApiInterface::class.java)

	}
}