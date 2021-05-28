package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.PostAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentPostProfileBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.other.*
import com.nurbk.ps.hashitaqalquds.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostProfileFragment : Fragment(), PostAdapter.OnListItemViewClickListener {
    val array: ArrayList<Post> = arrayListOf()
    fun addData(posts: ArrayList<Post>) {
        array.clear()
        array.addAll(posts)
        mAdapter.notifyDataSetChanged()
    }

    private val mBinding by lazy {
        FragmentPostProfileBinding.inflate(layoutInflater)
    }

    private val mAdapter by lazy {
        PostAdapter()
    }
    @Inject
    lateinit var viewModelHome: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.data = array
        mAdapter.onClick = this
        mBinding.rcPost.apply {
            adapter = mAdapter
        }
    }


    override fun onClickItem(post: Post, type: Int) {
        val bundle = Bundle()
        bundle.putParcelable(DATA_POST, post)
        when (type) {
            ACTION_PDF -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.media))
                requireActivity().startActivity(browserIntent)
            }
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
                    viewModelHome.addAction(post, true)
                } else {
                    post.likes.add(uid)
                    viewModelHome.addAction(post, false)
                }
                viewModelHome.update(post.id, mapOf("likes" to post.likes))
            }
        }
    }
}