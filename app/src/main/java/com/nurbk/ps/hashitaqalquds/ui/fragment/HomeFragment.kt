package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentHomeBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.model.Welcome
import com.nurbk.ps.hashitaqalquds.model.getData
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import com.nurbk.ps.hashitaqalquds.ui.dialog.LoadingDialog
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Post> {


    @Inject
    lateinit var viewModel: HomeViewModel
    private lateinit var loadingDialog: LoadingDialog

    private val mBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val mAdapter by lazy {
        GenericAdapter(R.layout.item_post_image, BR.post, this)
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
            title = getString(R.string.home),
            isMane = true,
            idMenu = R.drawable.ic_add
        ) {
            findNavController().navigate(R.id.action_homeFragment_to_dialogAddPost)
        }

        mBinding.rcData.apply {
            adapter = mAdapter
        }


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
                    } catch (e: Exception){}
                    val data = it.data as ArrayList<Post>
                    Log.e("tttttttt", data.toString() + "ttttttt")
                    mAdapter.data = data
                }
            }
        }
    }

    override fun onClickItem(itemViewModel: Post, type: Int, position: Int) {

    }
}