package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentProfileBinding
import com.nurbk.ps.hashitaqalquds.databinding.FragmentSettingBinding
import com.nurbk.ps.hashitaqalquds.other.TWO
import com.nurbk.ps.hashitaqalquds.other.setToolbarView

class SettingFragment : Fragment() {

    private val mBinding by lazy {
        FragmentSettingBinding.inflate(layoutInflater)
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
            title = getString(R.string.setting),
            isMane = false,
            idMenu = 0
        ) {
            if (it == TWO)
                findNavController().popBackStack()
        }
    }

}