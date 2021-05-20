package com.nurbk.ps.hashitaqalquds.other

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.nurbk.ps.hashitaqalquds.R


object HolderAdapter {


    @JvmStatic
    @BindingAdapter("loadImageRec")
    fun loadImage(image: ImageView, img: Int) {
        try {
            image.setImageResource(img)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}