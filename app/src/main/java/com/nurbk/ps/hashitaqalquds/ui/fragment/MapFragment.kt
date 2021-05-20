package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentHomeBinding
import com.nurbk.ps.hashitaqalquds.databinding.FragmentMapBinding
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(){

    private val mBinding by lazy {
        FragmentMapBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarView(
            mBinding.toolbarView,
            title = getString(R.string.map),
            isMane = true,
            idMenu = R.drawable.ic_replay
        ) {

        }
    }
}