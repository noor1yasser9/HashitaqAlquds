package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentLandmarkDetailsBinding
import com.nurbk.ps.hashitaqalquds.databinding.FragmentLandmarksBinding
import com.nurbk.ps.hashitaqalquds.other.setToolbarView


class LandmarksFragment : Fragment() {

    private val mBinding by lazy{
        FragmentLandmarkDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setToolbarView(
            mBinding.toolbarView,
            title = getString(R.string.landmark),
            isMane = true,
            idMenu = R.drawable.ic_add
        ){

        }
    }
}