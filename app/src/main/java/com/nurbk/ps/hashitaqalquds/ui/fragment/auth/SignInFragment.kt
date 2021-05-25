package com.nurbk.ps.hashitaqalquds.ui.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentSignInBinding
import com.nurbk.ps.hashitaqalquds.other.setToolbarView
import com.nurbk.ps.hashitaqalquds.viewmodel.AuthViewModel
import javax.inject.Inject

class SignInFragment : Fragment() {

    @Inject
    lateinit var viewMode: AuthViewModel


    private val mBinding by lazy {
        FragmentSignInBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mBinding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_homeFragment)
        }

        mBinding.createSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_signUpFragment)
        }

    }
}