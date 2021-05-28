package com.nurbk.ps.hashitaqalquds.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.BR
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.adapter.GenericAdapter
import com.nurbk.ps.hashitaqalquds.databinding.FragmentLandmarksBinding
import com.nurbk.ps.hashitaqalquds.model.Landmark
import com.nurbk.ps.hashitaqalquds.other.LANDMARK_KEY
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import com.nurbk.ps.hashitaqalquds.ui.dialog.LoadingDialog
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LandmarksFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Landmark> {

    @Inject
    lateinit var viewModel: MapViewModel

    private val mBinding by lazy {
        FragmentLandmarksBinding.inflate(layoutInflater)
    }
    private val mAdapter by lazy {
        GenericAdapter(R.layout.item_landmark, BR.landmark, this)
    }
    private lateinit var loadingDialog: LoadingDialog
    private var isShowData = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog()

        requireActivity().setToolbarView(
            mBinding.toolbarView,
            title = getString(R.string.landmark),
            isMane = false,
            idMenu = 0
        ) {
            findNavController().popBackStack()
        }
        viewModel.getLandmarkLiveDataGetLiveData.observe(viewLifecycleOwner) {
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
                    if (isShowData) {
                        mAdapter.data = (it.data as ArrayList<Landmark>)
                        isShowData = false
                    }
                }
            }
        }
        mBinding.rcLand.apply {
            adapter = mAdapter
        }
    }

    override fun onClickItem(itemViewModel: Landmark, type: Int, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable(LANDMARK_KEY,itemViewModel)
        findNavController().navigate(R.id.action_landmarksFragment_to_landmarkDetailsFragment,bundle)
    }
}