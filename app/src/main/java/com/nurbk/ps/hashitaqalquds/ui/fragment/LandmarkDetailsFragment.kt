package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentLandmarkDetailsBinding
import com.nurbk.ps.hashitaqalquds.model.Landmark
import com.nurbk.ps.hashitaqalquds.other.LANDMARK_KEY
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import com.nurbk.ps.hashitaqalquds.other.stateTheme
import com.nurbk.ps.hashitaqalquds.viewmodel.LandmarkViewModel
import kotlinx.android.synthetic.main.view_custom_marker.view.*
import javax.inject.Inject


class LandmarkDetailsFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<String> {

    @Inject
    lateinit var viewModel: LandmarkViewModel

    private lateinit var landmarks: Landmark
    private lateinit var mMap: MapView
    private val mBinding by lazy {

        FragmentLandmarkDetailsBinding.inflate(layoutInflater)
    }
    private val mAdapter by lazy {
        GenericAdapter(R.layout.item_landmark_image, BR.uri, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments().getParcelable<Landmark>(LANDMARK_KEY)?.let {
            landmarks = it
            mBinding.landmark = it
            if (landmarks.latLng.isNotEmpty()) {
                val location =
                    landmarks.latLng.split(",".toRegex()).toTypedArray()
                viewMap(savedInstanceState, LatLng(location[0].toDouble(), location[1].toDouble()),landmarks.name)
            }

        }

        requireActivity().setToolbarView(
            mBinding.toolbarView,
            title = landmarks.name,
            isMane = false,
            idMenu = 0
        ) {
            findNavController().popBackStack()
        }

        mAdapter.data = landmarks.images
        mBinding.vpImage.apply {
            adapter = mAdapter
            mBinding.dotsIndicator.setViewPager2(this)
        }
    }

    override fun onClickItem(itemViewModel: String, type: Int, position: Int) {
    }

    private fun viewMap(savedInstanceState: Bundle?, location: LatLng,title: String) {
        val map = mBinding.mapView
        map.onCreate(savedInstanceState)
        map.onResume()
        map.getMapAsync { mMap ->
            stateTheme(requireContext(), mMap)
            mMap.uiSettings.isScrollGesturesEnabled = false
            mMap.uiSettings.isZoomGesturesEnabled = false
            val mCustomMarkerView = (requireActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater).inflate(R.layout.view_custom_marker, null)
            mMap.addMarker(MarkerOptions().position(location))!!.setIcon(
                BitmapDescriptorFactory.fromBitmap(
                    getMarkerBitmapFromView(
                        mCustomMarkerView,title
                    )!!
                )
            )
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))
        }
    }

    private fun getMarkerBitmapFromView(view: View,title:String): Bitmap? {
//        mMarkerImageView.setImageBitmap(bitmap)
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        view.txtTitle.text = title
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