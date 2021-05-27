package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentLandmarkDetailsBinding
import com.nurbk.ps.hashitaqalquds.databinding.FragmentLandmarksBinding
import com.nurbk.ps.hashitaqalquds.model.Landmark
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import com.nurbk.ps.hashitaqalquds.viewmodel.LandmarkViewModel
import javax.inject.Inject


class LandmarkDetailsFragment : Fragment(),GenericAdapter.OnListItemViewClickListener<String> {

    @Inject
    lateinit var viewModel: LandmarkViewModel

    private lateinit var landmarks : Landmark
    private val mBinding by lazy{

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
        landmarks = Landmark()
        requireArguments().getParcelable<Landmark>("")?.let {
            landmarks = it
        }

        requireActivity().setToolbarView(
            mBinding.toolbarView,
            title = landmarks.name,
            isMane = false,
            idMenu = R.drawable.ic_add
        ){}

        mAdapter.data = landmarks.images
        mBinding.vpImage.apply {
            adapter = mAdapter
            mBinding.dotsIndicator.setViewPager2(this)
        }
    }

    override fun onClickItem(itemViewModel: String, type: Int, position: Int) {
    }

}