package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nurbk.ps.hashitaqalquds.adapter.PostAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentPostProfileBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import dagger.hilt.android.AndroidEntryPoint

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
    }
}