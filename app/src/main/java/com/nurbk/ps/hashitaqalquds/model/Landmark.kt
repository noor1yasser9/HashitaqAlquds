package com.nurbk.ps.hashitaqalquds.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Landmark(
    val name: String = "",
    val description: String = "",
    val images: ArrayList<String> = arrayListOf(),
    val latLng: String = ""
):Parcelable
