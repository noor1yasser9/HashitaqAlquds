package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentCommentsBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.other.DATA_POST
import com.nurbk.ps.hashitaqalquds.other.setToolbarView

class CommentFragment : Fragment() {

    private val mBinding by lazy {
        FragmentCommentsBinding.inflate(layoutInflater)
    }

    private lateinit var post: Post
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            }
        }
    }

}