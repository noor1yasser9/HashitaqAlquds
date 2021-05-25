package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentHomeBinding
import com.nurbk.ps.hashitaqalquds.model.Welcome
import com.nurbk.ps.hashitaqalquds.model.getData
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() ,GenericAdapter.OnListItemViewClickListener<Welcome> {
    private val mBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val mAdapter by lazy {
        GenericAdapter(R.layout.item_post_image, BR.welcome, this)
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
            title = getString(R.string.home),
            isMane = true,
            idMenu = R.drawable.ic_add
        ) {
            findNavController().navigate(R.id.action_homeFragment_to_dialogAddPost)
        }

        mAdapter.submitList(getData(requireContext()))

        mBinding.rcData.apply {
            adapter = mAdapter
        }

    }

    override fun onClickItem(itemViewModel: Welcome, type: Int, position: Int) {

    }
}