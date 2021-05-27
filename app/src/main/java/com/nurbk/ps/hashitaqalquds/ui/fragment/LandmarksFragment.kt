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


class LandmarksFragment : Fragment() ,GenericAdapter.OnListItemViewClickListener<Landmark>{

    @Inject
    lateinit var viewModel: LandmarkViewModel

    private val mBinding by lazy {
        FragmentLandmarksBinding.inflate(layoutInflater)
    }
    private val mAdapter by lazy{
        GenericAdapter(R.layout.item_landmark,BR.landmark,this)
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
        ) {}
        mBinding.rcLand.apply{
            adapter = mAdapter
        }
    }

    override fun onClickItem(itemViewModel: Landmark, type: Int, position: Int) {

    }
}