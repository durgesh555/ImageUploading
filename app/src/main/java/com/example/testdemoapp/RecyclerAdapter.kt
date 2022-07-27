package com.example.testdemoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testdemoapp.databinding.ItemsViewBinding

class RecyclerAdapter(val ctx : Context): RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

	class MyViewHolder(val binding: ItemsViewBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MyViewHolder {
		return MyViewHolder(ItemsViewBinding.inflate(LayoutInflater.from(ctx), parent, false))
	}

	override fun onBindViewHolder(holder : MyViewHolder , position : Int) {
		holder.binding.apply {
			profileImage.setImageResource(R.drawable.profile_logo)
			nameProfile.text = "Johnny Deep"
			subTitle.text = "1 minute ago"
			postImage.setImageResource(R.drawable.item_pic)
			iconLike.setImageResource(R.drawable.ic_like)
			like.text = "Like"
			commentIcon.setImageResource(R.drawable.ic_comment)
			comment.text = "Comment"
			iconShare.setImageResource(R.drawable.ic_share)
			share.text = "Share"
		}
	}

	override fun getItemCount() : Int {
	return 	20
	}
}