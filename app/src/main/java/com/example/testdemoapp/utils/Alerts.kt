package com.example.testdemoapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import okhttp3.MultipartBody
import java.io.File
object Alerts {

	fun toast(ctx : Context , msg : String) {
		Toast.makeText(ctx , msg , Toast.LENGTH_SHORT).show()
	}

	private var logStart = "\n\n<----------------------( LOG START )---------------------->\n\n"
	private var logEnd = "\n\n<----------------------( LOG END )---------------------->\n\n"
	fun log(tag:String, msg : String){
		Log.d(tag, logStart + msg + logEnd)
	}

}