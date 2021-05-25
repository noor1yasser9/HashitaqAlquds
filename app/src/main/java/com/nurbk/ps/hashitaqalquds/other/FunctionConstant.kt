package com.nurbk.ps.hashitaqalquds.other

import android.app.*
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.databinding.ToolbarLayoutBinding
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.ArrayList
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.ByteArrayOutputStream

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



