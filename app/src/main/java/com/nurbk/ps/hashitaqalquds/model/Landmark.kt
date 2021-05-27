package com.nurbk.ps.hashitaqalquds.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Landmark(
    val name: String = "",
    val details: String = "",
    val images: ArrayList<String> = arrayListOf(),
    val location: LatLng = LatLng(0.0,0.0),
):Parcelable
