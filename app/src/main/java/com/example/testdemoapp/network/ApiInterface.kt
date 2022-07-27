package com.example.testdemoapp.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {

	@Multipart
	@POST("/api/beta/content.php?cid=7ec99b415af3e88205250e3514ce0fa7")
	suspend fun uploadImage(
		@Part image : MultipartBody.Part
	) : ResponseApi

}
