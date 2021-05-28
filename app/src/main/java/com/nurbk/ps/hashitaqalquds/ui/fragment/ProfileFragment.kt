package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentProfileBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import com.nurbk.ps.hashitaqalquds.ui.dialog.LoadingDialog
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val TAG: String = ProfileFragment::class.java.name
    private var loading: String? = null
    private val myPostFragment = PostProfileFragment()
    private val myLikeFragment = PostProfileFragment()

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
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        requireActivity().setToolbarView(
            mBinding.toolbarView,
            title = getString(R.string.profile),
            isMane = true,
            idMenu = R.drawable.ic_settings
        ) {
            findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
        }

        pagerAdapter.addFragment(myLikeFragment, "")
        //  myLikeFragment.addData(arrayListOf(Post()))
        pagerAdapter.addFragment(myPostFragment, "")
        mBinding.vpProfile.apply {
            adapter = pagerAdapter
        }
        TabLayoutMediator(mBinding.tabLayout, mBinding.vpProfile) { tab, position ->
            when (position) {
                1 -> {
                    tab.text = "المنشورات"
                }
                0 -> tab.text = "التفاعلات"
            }
        }.attach()

        viewModel.getAllPostWhereUserId(mAuth.uid.toString())
        viewModel.getAllPostWhereUserIdGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                }
                Result.Status.ERROR -> {
                }
                Result.Status.LOADING -> {

                }
                Result.Status.SUCCESS -> {
                    myPostFragment.addData(it.data as ArrayList<Post>)
                }
            }
        }

        viewModel.getPostLikeUser(mAuth.uid.toString())
        viewModel.getPostLikesGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                }
                Result.Status.ERROR -> {
                }
                Result.Status.LOADING -> {

                }
                Result.Status.SUCCESS -> {
                    val data = it.data as ArrayList<Post>
                    myLikeFragment.addData(data)
                    myLikeFragment.mAdapter.notifyDataSetChanged()
                    Log.e("ttttttttttttttt", data.size.toString())
                }
            }
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
                        mBinding.user = it.data as User
                    }

                }
            }
        }
    }


    inner class ScreenSlidePagerAdapter(fa: FragmentActivity) :
        FragmentStateAdapter(fa) {
        private val lf: ArrayList<Fragment> = ArrayList()
        private val lt: ArrayList<String> = ArrayList()

        override fun createFragment(position: Int): Fragment {
            return lf[position]
        }

        override fun getItemCount(): Int {
            return lf.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            lf.add(fragment)
            lt.add(title)
        }

    }

}