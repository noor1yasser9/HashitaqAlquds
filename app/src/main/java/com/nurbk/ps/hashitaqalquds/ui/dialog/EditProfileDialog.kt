package com.nurbk.ps.hashitaqalquds.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nurbk.ps.hashitaqalquds.databinding.DialogEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileDialog :BottomSheetDialogFragment(){
    private val mBinding by lazy {
        DialogEditProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


}