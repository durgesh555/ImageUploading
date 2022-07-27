package com.example.testdemoapp

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testdemoapp.network.Resource
import com.example.testdemoapp.network.ResponseApi
import com.example.testdemoapp.repository.ActivityRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ActivityViewModel(private val repo : ActivityRepository) : ViewModel() {

	private var _responseApi = MutableLiveData<Resource<ResponseApi>>()
	val  responseApi: MutableLiveData<Resource<ResponseApi>>
		get() = _responseApi


	fun uploadImage(
		file: MultipartBody.Part
	) = viewModelScope.launch {
		_responseApi.value = repo.uploadImage(file)
	}


}