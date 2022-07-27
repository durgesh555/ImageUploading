package com.example.testdemoapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.testdemoapp.databinding.ActivityMainBinding
import com.example.testdemoapp.fragments.HomeFragment
import com.example.testdemoapp.fragments.UploadPhotoFragment
import com.example.testdemoapp.network.ApiInterface
import com.example.testdemoapp.network.RetrofitService
import com.example.testdemoapp.repository.ActivityRepository
import com.example.testdemoapp.utils.Alerts
import com.example.testdemoapp.utils.Constants
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
	lateinit var viewModel : ActivityViewModel
	lateinit var binding : ActivityMainBinding
	private lateinit var imageUri : Uri
	 private val fragmentManager = supportFragmentManager

	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		permissionRequest()

		val repoService = RetrofitService.retrofitInstance()
		val repo = ActivityRepository(repoService)
		viewModel = ViewModelProvider(this , MainViewModelFactory(repo))[ActivityViewModel::class.java]

		val fragment = HomeFragment()
		fragmentManager.beginTransaction().add(R.id.fragment_container , fragment).commit()
	}

	fun permissionRequest(){
		if (EasyPermissions.hasPermissions(this, *Constants.params)){
			Alerts.toast(this, "Permission Granted.")
		}else{
			EasyPermissions.requestPermissions(this, "We need permission", Constants.REQUEST_PERMISSION, *Constants.params)
		}
	}


	override fun onRequestPermissionsResult(requestCode : Int , permissions : Array<out String> , grantResults : IntArray) {
		super.onRequestPermissionsResult(requestCode , permissions , grantResults)
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
	}

	override fun onPermissionsGranted(requestCode : Int , perms : MutableList<String>) {
		Alerts.toast(this, "Permission Granted.")
	}

	override fun onPermissionsDenied(requestCode : Int , perms : MutableList<String>) {
		if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
			AppSettingsDialog.Builder(this).build().show()
		}
	}

}