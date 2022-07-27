package com.example.testdemoapp.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testdemoapp.ActivityViewModel
import com.example.testdemoapp.MainViewModelFactory
import com.example.testdemoapp.databinding.FragmentUploadPhotoBinding
import com.example.testdemoapp.network.Resource
import com.example.testdemoapp.network.ResponseApi
import com.example.testdemoapp.network.RetrofitService
import com.example.testdemoapp.repository.ActivityRepository
import com.example.testdemoapp.utils.Alerts
import com.example.testdemoapp.utils.RealPathUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File


class UploadPhotoFragment : Fragment() {
	lateinit var binding : FragmentUploadPhotoBinding
	lateinit var viewModel : ActivityViewModel
	private lateinit var imageUri : Uri
	private var selectedImage : String? = null
	private var file : File? = null
	override fun onCreateView(
		inflater : LayoutInflater , container : ViewGroup? ,
		savedInstanceState : Bundle? ,
	) : View? {
		// Inflate the layout for this fragment
		binding = FragmentUploadPhotoBinding.inflate(layoutInflater , container , false)

		val repoService = RetrofitService.retrofitInstance()
		val repo = ActivityRepository(repoService)
		viewModel = ViewModelProvider(this , MainViewModelFactory(repo))[ActivityViewModel::class.java]

		binding.viewImage.setOnClickListener {
			openCamera()
		}

		binding.camera.setOnClickListener {
			openCamera()
		}

		binding.btnUpload.setOnClickListener {
			Alerts.log(javaClass.simpleName , ".....Request path ${file !!.name}${imagePart("image" , file !!.name , file !!)} ")
			viewModel.uploadImage(
				imagePart("image" , file !!.name , file !!)
			)
		}

		viewModel.responseApi.observe(viewLifecycleOwner , reponseApiObserver)
		return binding.root
	}

	private val reponseApiObserver = Observer<Resource<ResponseApi>> {
		when (it) {
			is Resource.Success -> {
				Alerts.log(javaClass.simpleName , "........Api reponse  ${it.value}")
				if (it.value.statusCode == 200) {
					Alerts.toast(requireContext() , "Upload Image Successfully. ${it.value.message}")
				}
			}
			is Resource.Error -> {
				try {
					if (it.isNetworkError) {
						Alerts.log(javaClass.simpleName , "ERROR Network : \n${it.errorCode}")
						Alerts.toast(requireContext() , "Network Error")

					}
					else {
						try {
							val err = it.errorBody !!.charStream().readText()
							Alerts.log(javaClass.simpleName , "ERROR RAW : $err")
							val jsonObj = JSONObject(err)
							Alerts.log(javaClass.simpleName , "ERROR JSON : \n${jsonObj}")
							Alerts.toast(requireContext() , "Error ${jsonObj.getString("message")}")
						} catch (e : Exception) {
							e.printStackTrace()
						}
					}
				} catch (e : Exception) {
					e.printStackTrace()
				}
			}
		}

	}

	fun imagePart(param : String , name : String , file : File) : MultipartBody.Part {
		val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
		val part = MultipartBody.Part.createFormData(param , name , fileReqBody)
		return part
	}

	private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult() , ActivityResultCallback { result ->
		if (result.resultCode == RESULT_OK && result.data != null) {

			val image = result.data !!.extras!!.get("data") as Bitmap
			selectedImage = RealPathUtil.getRealPath(requireContext() , getImageUri(requireContext() , image) !!)
			binding.viewImage.setImageBitmap(image)

			 file = File(Uri.parse(selectedImage).path.toString())
			Alerts.log(javaClass.simpleName, "image uri ...... $selectedImage")
		}
	})


	private fun openCamera() {
		resultLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
	}

	fun getImageUriFromBitmap(context: Context , bitmap: Bitmap): Uri {
		val bytes = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
		val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
		return Uri.parse(path.toString())
	}


	fun getImageUri(context : Context , bitmap : Bitmap?) : Uri? {
		val path = MediaStore.Images.Media.insertImage(context.contentResolver , bitmap , "myImage" , "")
		return Uri.parse(path)
	}

}

