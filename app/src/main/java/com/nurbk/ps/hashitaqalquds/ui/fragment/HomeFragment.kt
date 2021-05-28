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
import com.nurbk.ps.hashitaqalquds.adapter.PostAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentHomeBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.model.Welcome
import com.nurbk.ps.hashitaqalquds.model.getData
import com.nurbk.ps.hashitaqalquds.other.*
import com.nurbk.ps.hashitaqalquds.ui.dialog.LoadingDialog
import com.nurbk.ps.hashitaqalquds.util.MemberItemDecoration
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), PostAdapter.OnListItemViewClickListener {


    @Inject
    lateinit var viewModel: HomeViewModel
    private lateinit var loadingDialog: LoadingDialog

    private val mBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }


    private var mAdapter: PostAdapter = PostAdapter()
    private var isShowData = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog()
        mAdapter.onClick = this

        requireActivity().setToolbarView(
            mBinding.toolbarView,
            title = getString(R.string.home),
            isMane = true,
            idMenu = R.drawable.ic_add
        ) {
            findNavController().navigate(R.id.action_homeFragment_to_dialogAddPost)
        }

        mBinding.rcData.apply {
            adapter = mAdapter
            addItemDecoration(MemberItemDecoration());
        }

        isShowData = true
        viewModel.getAllPostsGetLiveData.observe(viewLifecycleOwner) {
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
                    try {
                        loadingDialog.dismiss()
                    } catch (e: Exception) {
                    }
                    val data = it.data as ArrayList<Post>
                    if (isShowData)
                        mAdapter.data = data
                    isShowData = false
                }
            }
        }
    }

    override fun onClickItem(post: Post, type: Int) {
        val bundle = Bundle()
        bundle.putParcelable(DATA_POST, post)
        when (type) {
            ACTION_PROFILE -> {
            }
            ACTION_MORE -> {
            }
            ACTION_COMMENT -> {
                findNavController().navigate(R.id.action_homeFragment_to_commentFragment, bundle)
            }
            ACTION_LIKE -> {
                val uid = FirebaseAuth.getInstance().uid.toString()
                if (post.likes.contains(uid)) {
                    post.likes.remove(uid)
                    viewModel.addAction(post, true)
                } else {
                    post.likes.add(uid)
                    viewModel.addAction(post, false)
                }
                viewModel.update(post.id, mapOf("likes" to post.likes))
            }
        }
    }

}