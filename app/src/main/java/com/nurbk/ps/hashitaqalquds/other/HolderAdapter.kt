package com.nurbk.ps.hashitaqalquds.other

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import cn.jzvd.JZVideoPlayerStandard
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.danikula.videocache.HttpProxyCacheServer
import com.nurbk.ps.hashitaqalquds.BaseApplication
import com.nurbk.ps.hashitaqalquds.util.PreferencesManager


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
    fun uriVideo(jzVideoPlayerStandard: JZVideoPlayerStandard, uri: String) {
        try {

            jzVideoPlayerStandard.setUp(
                uri,
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                ""
            )
            Glide.with(jzVideoPlayerStandard.context).load(uri)
                .into(jzVideoPlayerStandard.thumbImageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter("onSwitchCompat")
    fun onSwitchCompat(switch: SwitchCompat, isSwitch: Boolean) {
        switch.isVisible = isSwitch
        val shared = PreferencesManager(switch.context)
        switch.isChecked = shared.isDark
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            shared.editor.putBoolean(IS_DARK, isChecked).apply()
            if (isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}