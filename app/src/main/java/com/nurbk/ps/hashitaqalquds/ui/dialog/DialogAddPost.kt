package com.nurbk.ps.hashitaqalquds.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nurbk.ps.hashitaqalquds.databinding.DialogAddPostBinding
import com.nurbk.ps.hashitaqalquds.viewmodel.AddPostViewModel
import com.nurbk.ps.hashitaqalquds.viewmodel.AuthViewModel
import javax.inject.Inject

class DialogAddPost :BottomSheetDialogFragment() {

    @Inject
    lateinit var viewMode: AddPostViewModel


    private val mBinding by lazy {
        DialogAddPostBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


}