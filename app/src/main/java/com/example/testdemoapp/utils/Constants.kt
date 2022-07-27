package com.example.testdemoapp.utils

import android.Manifest

object Constants {
	const val BASE_URL = "https://dev.nojoto.com"
	const val REQUEST_PERMISSION = 101

	val params = arrayOf(
		Manifest.permission.CAMERA ,
		Manifest.permission.READ_EXTERNAL_STORAGE ,
		Manifest.permission.WRITE_EXTERNAL_STORAGE
	)
}