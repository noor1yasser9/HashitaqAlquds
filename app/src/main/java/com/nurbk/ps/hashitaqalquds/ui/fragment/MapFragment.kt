package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentMapBinding
import com.nurbk.ps.hashitaqalquds.model.Landmark
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import com.nurbk.ps.hashitaqalquds.ui.dialog.LoadingDialog
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Inject
    lateinit var viewModel: MapViewModel

    private val mBinding by lazy {
        FragmentMapBinding.inflate(layoutInflater)
    }
    private lateinit var loadingDialog: LoadingDialog
    private var isShowData = true
    private lateinit var mCustomMarkerView: View
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        mCustomMarkerView = (requireActivity().getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater).inflate(R.layout.view_custom_marker, null)

        requireActivity().setToolbarView(
            mBinding.toolbarView,
            title = getString(R.string.map),
            isMane = true,
            idMenu = R.drawable.ic_replay
        ) {
            findNavController().navigate(R.id.action_mapFragment_to_landmarksFragment)
        }
        viewModel.getLandmarkLiveDataGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                }
                Result.Status.ERROR -> {
                    loadingDialog.dismiss()
                }
                Result.Status.LOADING -> {
                    if (!loadingDialog.isAdded)
                        loadingDialog.show(requireActivity().supportFragmentManager, "")
                }
                Result.Status.SUCCESS -> {
                    try {
                        loadingDialog.dismiss()
                    } catch (e: Exception) {
                    }
                    if (isShowData) {
                        (it.data as ArrayList<Landmark>).forEach { landmark ->
                            if(landmark.latLng.isNotEmpty()){
                            val location =
                                landmark.latLng.split(",".toRegex()).toTypedArray()
                            addMarker(
                                LatLng(location[0].toDouble(), location[1].toDouble()),
                                landmark.name
                            )
                            }
                        }
                    }
                    isShowData = false
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setPadding(20, 30, 30, 340)
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

    }

    private fun addMarker(position: LatLng, title: String) {
        mMap.addMarker(
            MarkerOptions()
                .position(position)
                .title(title)
        )!!.setIcon(
            BitmapDescriptorFactory.fromBitmap(
                getMarkerBitmapFromView(
                    mCustomMarkerView
                )!!
            )
        )
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.e("kkkkkk",marker.title!!)
        return false
    }

    private fun getMarkerBitmapFromView(view: View): Bitmap? {
//        mMarkerImageView.setImageBitmap(bitmap)
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        val returnedBitmap = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawable = view.background
        drawable?.draw(canvas)
        view.draw(canvas)
        return returnedBitmap
    }
}