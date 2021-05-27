package com.nurbk.ps.hashitaqalquds.other

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nurbk.ps.hashitaqalquds.R
import com.potyvideo.library.AndExoPlayerView
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard


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

    @JvmStatic
    @BindingAdapter("uriImage")
    fun uriImage(image: ImageView, img: String?) {
        try {
            Glide
                .with(image.context)
                .load(img)
                .centerCrop()
                .into(image)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter("uriVideo")
    fun uriVideo(videoPlayer: AndExoPlayerView , uri: String) {
        try {
            videoPlayer.setSource(uri);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}