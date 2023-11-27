package com.hokari.customer.utils

import android.content.Context

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hokari.customer.R

import java.io.IOException

class LoadGlide(val context: Context){

    fun loadUserPicture(image: Any, imageView: ImageView) {
        try {
            Glide
                .with(context)
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_placeholder)
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadProductImage(image: Any, imageView: ImageView) {
        try {
            Glide
                .with(context)
                .load(image)
                .centerCrop()
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
