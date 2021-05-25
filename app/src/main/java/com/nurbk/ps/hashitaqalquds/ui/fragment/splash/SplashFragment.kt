package com.nurbk.ps.hashitaqalquds.ui.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentSplashBinding
import com.nurbk.ps.hashitaqalquds.util.PreferencesManager
import com.nurbk.ps.hashitaqalquds.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val mBinding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animZoomIn: Animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.zoom_in
        )
        viewModel.run {
            liveData.run {
                observe(requireActivity(), {
                    when (it) {
                        is SplashViewModel.SplashState.SplashFragment -> {
                            if (PreferencesManager(requireContext()).isLogin) {
                                Navigation.findNavController(requireView())
                                    .navigate(R.id.action_splashFragment_to_homeFragment)
                            } else
                                findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
                        }
                    }
                })
                observe(requireActivity(), {
                    when (it) {
                        is SplashViewModel.SplashState.SplashFragment -> {

                            mBinding.logoImageView.startAnimation(animZoomIn)
                        }
                    }
                })
            }

        }


    }
}