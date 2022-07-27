package com.example.testdemoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testdemoapp.R
import com.example.testdemoapp.RecyclerAdapter
import com.example.testdemoapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

	lateinit var binding : FragmentHomeBinding

	override fun onCreateView(
		inflater : LayoutInflater , container : ViewGroup? ,
		savedInstanceState : Bundle? ,
	) : View? {
		// Inflate the layout for this fragment
		binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

		binding.recyclerView.adapter = RecyclerAdapter(ctx = requireContext())
		val fragment = UploadPhotoFragment()
		binding.camera.setOnClickListener {
			activity?.supportFragmentManager?.beginTransaction()?.apply {
				replace(R.id.fragment_container, fragment, UploadPhotoFragment::class.java.simpleName)
					.addToBackStack(null)
					.commit()
			}

		}

		return binding.root
	}

}