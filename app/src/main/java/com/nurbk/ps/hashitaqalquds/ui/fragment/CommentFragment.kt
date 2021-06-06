package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentCommentsBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.other.*
import com.nurbk.ps.hashitaqalquds.ui.dialog.LoadingDialog
import com.nurbk.ps.hashitaqalquds.util.PreferencesManager
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.AddPostViewModel
import com.nurbk.ps.hashitaqalquds.viewmodel.CommentViewModel
import com.nurbk.ps.hashitaqalquds.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CommentFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Post> {

    @Inject
    lateinit var viewMode: CommentViewModel

    private val mBinding by lazy {
        FragmentCommentsBinding.inflate(layoutInflater)
    }
    private val mAdapter by lazy {
        GenericAdapter(R.layout.item_post_h, BR.post, this)
    }

    lateinit var onShowSnack: OnShowSnack
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnShowSnack)
            onShowSnack = context

    }


    private var loading: String? = null
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var post: Post
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
            title = getString(R.string.enterComment),
            isMane = false,
            idMenu = 0
        ) {
            findNavController().popBackStack()
        }


        requireArguments().apply {
            getParcelable<Post>(DATA_POST)?.let {
                post = it
                mBinding.post = post
                viewMode.getComments(post.id)
            }
        }

        viewMode.getCommentsGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                    loadingDialog.dismiss()
                }
                Result.Status.ERROR -> {
                    loadingDialog.dismiss()
                }
                Result.Status.LOADING -> {
                    loading = it.data.toString()
                    //   if (!loadingDialog.isAdded)
                    //  loadingDialog.show(requireActivity().supportFragmentManager, "")
                }
                Result.Status.SUCCESS -> {
                    if (loading != null) {
                        try {
                            loadingDialog.dismiss()
                        } catch (e: Exception) {
                        }
                        mAdapter.data = it.data as ArrayList<Post>

                    }
                }
            }
        }
        mBinding.rcData.apply {
            adapter = mAdapter
        }
        viewMode.addCommentGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                }
                Result.Status.ERROR -> {
                    //   loadingDialog.dismiss()
                }
                Result.Status.LOADING -> {
                    loading = it.data.toString()
                    if (!loadingDialog.isAdded)
                        loadingDialog.show(requireActivity().supportFragmentManager, "")
                }
                Result.Status.SUCCESS -> {
                    if (loading != null) {
                        try {
                            loadingDialog.dismiss()
                        } catch (e: Exception) {
                        }
                        // findNavController().popBackStack()
                    }
                }
            }
        }
        mBinding.txtComment.setOnEditorActionListener { _, _, _ ->
            if (PreferencesManager(requireContext()).isLogin) {
                if (mBinding.txtComment.text.isNotEmpty()) {
                    addComment()
                    mBinding.txtComment.text.clear()
                    mBinding.txtComment.clearFocus()
                }

            } else {
                onShowSnack.onShowSnack()
            }
            true
        }
    }

    fun addComment() {
        val content = mBinding.txtComment.text.toString().trim()
        val comment = Post(
            id = FirebaseFirestore.getInstance().collection(COLLECTION_POST).document().collection(
                COMMENT_POST
            ).document().id,
            userId = FirebaseAuth.getInstance().uid.toString(),
            date = Calendar.getInstance().time.time,
            tag = post.tag, content = content, media = null, NULL_TYPE
        )
        viewMode.addComment(post, comment)
    }

    override fun onClickItem(itemViewModel: Post, type: Int, position: Int) {

    }

}