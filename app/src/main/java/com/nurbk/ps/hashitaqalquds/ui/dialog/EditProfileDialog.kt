package com.nurbk.ps.hashitaqalquds.ui.dialog

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.DialogEditProfileBinding
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.other.*
import com.nurbk.ps.hashitaqalquds.other.HolderAdapter.uriImage
import com.nurbk.ps.hashitaqalquds.repository.UserRepository
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileDialog : BottomSheetDialogFragment() {
    private val TAG: String = EditProfileDialog::class.java.name
    private var media: Uri? = null
    private val mBinding by lazy {
        DialogEditProfileBinding.inflate(layoutInflater)
    }
    private lateinit var user: User
    private val intent by lazy {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }
    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private lateinit var loadingDialog: LoadingDialog

    @Inject
    lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog()
        viewModel.getWhereId(mAuth.uid.toString())
        viewModel.getWhereIdGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                }
                Result.Status.ERROR -> {
                }
                Result.Status.LOADING -> {

                }
                Result.Status.SUCCESS -> {
                    mBinding.user = it.data as User
                }
            }
        }
        viewModel.updateGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                }
                Result.Status.ERROR -> {
                }
                Result.Status.LOADING -> {

                }
                Result.Status.SUCCESS -> {
                    dismiss()
                }
            }
        }
        mBinding.btnSave.setOnClickListener {
            createAccount()
        }
        mBinding.imgProfile.setOnClickListener {
            permission(
                requireContext(),
                arrayListOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), onComplete = {
                    selectImage()
                }, {}
            )
        }

    }

    private fun createAccount() {
        val name: String = mBinding.txtName.editText!!.text.toString()
        val bio: String = mBinding.txtNote.editText!!.text.toString().trim()
        val phone: String = mBinding.txtPhone.editText!!.text.toString()
        if (TextUtils.isEmpty(name)) {
            errorText(mBinding.txtName.editText!!, getString(R.string.field_required))
        } else if (TextUtils.isEmpty(bio)) {
            errorText(mBinding.txtNote.editText!!, getString(R.string.field_required))
        } else if (TextUtils.isEmpty(phone)) {
            errorText(mBinding.txtPhone.editText!!, getString(R.string.field_required))
        } else if (Pattern.matches("[a-zA-Z-+]+", phone)
            || phone.length != 12
        ) {
            errorText(mBinding.txtPhone.editText!!, getString(R.string.error_phone))
        } else {
            val map = mutableMapOf<String, Any>()
            if (user.name != name) map["name"] = name
            if (user.phone != phone) map["phone"] = phone
            if (user.bio != bio) map["bio"] = bio

            if (media == null) {
                viewModel.update(mAuth.uid!!, map)
                Log.e(TAG, "onSuccess: not uploadImage")
            } else {
                viewModel.uploadImage(user.image, compressFormat(media!!, requireActivity())) {
                    Log.e(TAG, "onSuccess: uploadImage")
                    map["image"] = it
                    viewModel.update(mAuth.uid!!, map)
                }
            }
        }
    }

    private fun errorText(text: EditText, message: String) {
        text.error = message
        text.requestFocus()
    }

    private fun selectImage() {
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CODE &&
            resultCode == Activity.RESULT_OK
        ) {

            media = data!!.data!!
            val imageStream = requireActivity().contentResolver.openInputStream(media!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            mBinding.imgProfile.setImageURI(media)

        }
    }
}