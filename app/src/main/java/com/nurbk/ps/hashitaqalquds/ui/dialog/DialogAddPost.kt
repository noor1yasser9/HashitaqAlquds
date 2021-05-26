package com.nurbk.ps.hashitaqalquds.ui.dialog

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.DialogAddPostBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.other.*
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.AddPostViewModel
import com.nurbk.ps.hashitaqalquds.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DialogAddPost : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewMode: AddPostViewModel
    private var media: Uri? = null
    private lateinit var loadingDialog: LoadingDialog
    private val mBinding by lazy {
        DialogAddPostBinding.inflate(layoutInflater)
    }

    private var type = CONTENT_TYPE


    private val intent by lazy {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog()

        mBinding.btnShare.setOnClickListener {
            addPost()
        }


        viewMode.insertPostGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
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
                    findNavController().popBackStack()
                }
            }
        }

        mBinding.btnImage.setOnClickListener {
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

        mBinding.btnVideo.setOnClickListener {
            permission(
                requireContext(),
                arrayListOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), onComplete = {
                    selectVideo()
                }, {}
            )
        }

        mBinding.btnAddFile.setOnClickListener {
            permission(
                requireContext(),
                arrayListOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), onComplete = {
                    selectFile()
                }, {}
            )
        }
    }

    private fun selectImage() {
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_CODE)
    }

    private fun selectVideo() {
        intent.type = "video/*"
        startActivityForResult(intent, REQUEST_VIDEO_CODE)

    }

    private fun selectFile() {
        val mIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        mIntent.type = "application/*"
        startActivityForResult(mIntent, REQUEST_FILE_CODE)

    }
    private fun addPost() {
        val hash = mBinding.listHash.selectedItem.toString()
        val content = mBinding.txtContent.text.toString().trim()

        if (TextUtils.isEmpty(content) && media != null) {
            Snackbar.make(requireView(), getString(R.string.error_post), Snackbar.LENGTH_LONG)
                .show()
        } else {

            val post = Post(
                id = FirebaseFirestore.getInstance().collection(COLLECTION_POST).document().id,
                userId = FirebaseAuth.getInstance().uid.toString(),
                date = Calendar.getInstance().time.time,
                tag = hash, content = content, media = media.toString(), type
            )
            when (type) {
                CONTENT_TYPE -> {
                    viewMode.insert(
                        post
                    )
                }
              PHOTO_TYPE -> {
                    viewMode.uploadImage(compressFormat(media!!, requireActivity())) {
                        post.media = it
                        viewMode.insert(
                            post
                        )

                    }

                }
                VIDEO_TYPE -> {
                    viewMode.uploadVideo(media!!, "mp4", {
                        post.media = it
                        viewMode.insert(
                            post
                        )
                    }, {

                    })
                }
              PDF_TYPE -> {
                    viewMode.uploadVideo(media!!, "pdf", {
                        post.media = it
                        viewMode.insert(
                            post
                        )
                    }, {

                    })
                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CODE &&
            resultCode == Activity.RESULT_OK
        ) {
            type = PHOTO_TYPE
            media = data!!.data!!
        } else if (requestCode == REQUEST_VIDEO_CODE &&
            resultCode == Activity.RESULT_OK
        ) {
            type = VIDEO_TYPE
            media = data!!.data!!
        }
        else if (requestCode == REQUEST_FILE_CODE &&
            resultCode == Activity.RESULT_OK
        ) {
            type =PDF_TYPE
            media = data!!.data!!
        }
    }
}