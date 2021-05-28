package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.adapter.PostAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentPostProfileBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostProfileFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Post> {
    var array: ArrayList<Post> = arrayListOf()
    fun addData(posts: ArrayList<Post>) {
        array = (posts)
        mAdapter.notifyDataSetChanged()

    }

    private val mBinding by lazy {
        FragmentPostProfileBinding.inflate(layoutInflater)
    }

    var mAdapter: PostAdapter = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.data = array
        mAdapter.notifyDataSetChanged()
        mBinding.rcPost.apply {
            adapter = mAdapter
        }
    }

    override fun onClickItem(itemViewModel: Post, type: Int, position: Int) {

    }
}