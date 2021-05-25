package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentSettingBinding
import com.nurbk.ps.hashitaqalquds.model.Settings
import com.nurbk.ps.hashitaqalquds.model.getDataStings
import com.nurbk.ps.hashitaqalquds.other.*
import com.nurbk.ps.hashitaqalquds.util.PreferencesManager
import com.nurbk.ps.hashitaqalquds.viewmodel.ProfileViewModel
import javax.inject.Inject

class SettingFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Settings> {

    @Inject
    lateinit var viewModel: ProfileViewModel

    private val mBinding by lazy {
        FragmentSettingBinding.inflate(layoutInflater)
    }
    private val mAdapter by lazy {
        GenericAdapter(R.layout.item_setting, BR.setting, this)
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

        mAdapter.submitList(getDataStings(requireContext()))

        mBinding.rcSetting.apply {
            adapter = mAdapter
        }
    }

    override fun onClickItem(itemViewModel: Settings, type: Int, position: Int) {
        when (position) {
            ZERO -> {
                findNavController().navigate(R.id.action_settingFragment_to_editProfileDialog)
            }
            ONE -> {

            }
            TWO -> {

            }
            THREE -> {
                FirebaseAuth.getInstance().signOut()
                PreferencesManager(requireContext()).logOut()
                findNavController().navigate(R.id.action_settingFragment_to_welcomeFragment)
            }
        }
    }

}