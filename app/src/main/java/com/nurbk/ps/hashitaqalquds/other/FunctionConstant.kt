package com.nurbk.ps.hashitaqalquds.other

import android.app.*
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import com.nurbk.ps.hashitaqalquds.databinding.ToolbarLayoutBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.nurbk.ps.hashitaqalquds.R
import java.io.ByteArrayOutputStream
import java.util.*

fun Activity.setToolbarView(
    view: ToolbarLayoutBinding,
    title: String,
    isMane: Boolean = false,
    idMenu: Int,
    onComplete: (Int) -> Unit
): ToolbarLayoutBinding {


    view.tvTitle.text = title
    view.btnBack.isVisible = !isMane
    view.btnBack.setOnClickListener {
        onComplete(TWO)
    }
    if (idMenu != 0) {
        view.btnMenu.isVisible = true
        view.menu.setImageResource(idMenu)
        view.btnMenu.setOnClickListener {
            onComplete(ONE)
        }
    }

    return view
}



fun permission(
    context: Context,
    permission: ArrayList<String>,
    onComplete: () -> Unit,
    onDenied: () -> Unit
) {
    Dexter.withContext(context)
        .withPermissions(
            permission
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (report.areAllPermissionsGranted()) {
                        onComplete()

                    } else {
                        onDenied()
                        Log.e("ttttttonDenied", "onDenied")
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {

                token?.continuePermissionRequest()
            }
        })
        .withErrorListener {

        }
        .check()
}


fun compressFormat(data: Uri, activity: Activity): ByteArray {
    val selectImageBmp = MediaStore
        .Images.Media.getBitmap(
            activity.contentResolver, data
        )
    val outputStream = ByteArrayOutputStream()
    selectImageBmp
        .compress(Bitmap.CompressFormat.JPEG, 20, outputStream)
    return outputStream.toByteArray()
}


fun generateColor(view: View, context: Context) {
    val rnd = Random()
    val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    val shape = ContextCompat.getDrawable(
        context,
        R.drawable.bg_letter
    )
    shape!!.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY)
    view.background = shape
}


fun stateTheme(context: Context, googleMap: GoogleMap) {
    val nightModeFlags = context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK
    when (nightModeFlags) {
        Configuration.UI_MODE_NIGHT_YES -> {
            googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context, R.raw.style_json
                )
            )
        }
    }
}



