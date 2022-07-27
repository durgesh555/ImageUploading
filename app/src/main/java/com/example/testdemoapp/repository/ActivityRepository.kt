package com.example.testdemoapp.repository

import com.example.testdemoapp.network.ApiInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ActivityRepository (private var api: ApiInterface): BaseRepository(){
	suspend fun uploadImage(
		file : MultipartBody.Part
	) = calls { api.uploadImage(file) }

}