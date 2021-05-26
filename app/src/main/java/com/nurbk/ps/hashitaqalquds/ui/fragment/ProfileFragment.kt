package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentProfileBinding
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.other.HolderAdapter.uriImage
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import com.nurbk.ps.hashitaqalquds.ui.dialog.LoadingDialog
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val TAG: String = ProfileFragment::class.java.name

    @Inject
    lateinit var viewModel: ProfileViewModel
    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private lateinit var loadingDialog: LoadingDialog


    private val mBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog()

        requireActivity().setToolbarView(
            mBinding.toolbarView,
            title = getString(R.string.profile),
            isMane = true,
            idMenu = R.drawable.ic_settings
        ) {
            findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
        }
        viewModel.getWhereId(mAuth.uid.toString())
        viewModel.getWhereIdGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                    loadingDialog.dismiss()
                }
                Result.Status.ERROR -> {
                    loadingDialog.dismiss()
                }
                Result.Status.LOADING -> {
                    if (!loadingDialog.isAdded)
                        loadingDialog.show(requireActivity().supportFragmentManager, "")
                }
                Result.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    val user = it.data as User
                    mBinding.txtName.text = user.name
                    mBinding.txtEmail.text = user.email
                    mBinding.txtPhone.text = user.phone
                    mBinding.txtNote.text = user.bio
                    uriImage(mBinding.imgProfile, user.image)
                    Log.e(TAG, user.image)

                }
            }
        }
    }
}