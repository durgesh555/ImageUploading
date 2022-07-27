package com.example.testdemoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testdemoapp.repository.ActivityRepository

class MainViewModelFactory(private val repo: ActivityRepository) : ViewModelProvider.Factory{
	override fun <T : ViewModel> create(modelClass : Class<T>) : T {
		return ActivityViewModel(repo) as T
	}
}