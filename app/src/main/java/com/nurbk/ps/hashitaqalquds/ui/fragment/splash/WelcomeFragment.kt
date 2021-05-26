package com.nurbk.ps.hashitaqalquds.ui.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentWelcomeBinding
import com.nurbk.ps.hashitaqalquds.model.Welcome
import com.nurbk.ps.hashitaqalquds.model.getData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Welcome> {

    private val mBinding by lazy {
        FragmentWelcomeBinding.inflate(layoutInflater)
    }

    private val mAdapter by lazy {
        GenericAdapter(R.layout.item_welcome, BR.welcome, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter.data = (getData(requireContext()))

        mBinding.vpWelcome.apply {
            adapter = mAdapter
            mBinding.dotsIndicator.setViewPager2(this)
        }

        mBinding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_signInFragment)
        }


    }


    override fun onClickItem(itemViewModel: Welcome, type: Int, position: Int) {

    }
}