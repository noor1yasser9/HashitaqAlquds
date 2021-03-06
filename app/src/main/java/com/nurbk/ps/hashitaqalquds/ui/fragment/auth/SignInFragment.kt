package com.nurbk.ps.hashitaqalquds.ui.fragment.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentSignInBinding
import com.nurbk.ps.hashitaqalquds.ui.dialog.LoadingDialog
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignInFragment : Fragment() {

    @Inject
    lateinit var viewModel: AuthViewModel
    private lateinit var loadingDialog: LoadingDialog

    private val mBinding by lazy {
        FragmentSignInBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()

        viewModel.signInGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                }
                Result.Status.ERROR -> {
                    loadingDialog.dismiss()
                }
                Result.Status.LOADING -> {
                    isLoading = true
                    if (!loadingDialog.isAdded)
                        loadingDialog.show(requireActivity().supportFragmentManager, "")
                }
                Result.Status.SUCCESS -> {
                    if (isLoading) {
                        try {

                            loadingDialog.dismiss()
                        } catch (e: Exception) {

                        }
                        findNavController().navigate(R.id.action_LoginFragment_to_homeFragment)
                    }
                }
            }

        }
        mBinding.btnSignIn.setOnClickListener {
            validate()
        }

        mBinding.createSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_signUpFragment)
        }
        mBinding.btnGaset.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_homeFragment)
        }

    }

    private fun validate() {
        val email: String = mBinding.txtEmail.editText!!.text.toString()
        val password: String = mBinding.txtPassword.editText!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            errorText(mBinding.txtEmail.editText!!, getString(R.string.field_required))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorText(mBinding.txtEmail.editText!!, getString(R.string.error_email))
        } else if (TextUtils.isEmpty(password)) {
            errorText(mBinding.txtPassword.editText!!, getString(R.string.field_required))
        } else if (password.length < 6) {
            errorText(mBinding.txtPassword.editText!!, getString(R.string.password_error))
        } else {
            viewModel.signIn(email, password)
        }
    }

    private fun errorText(editText: EditText, massage: String) {
        editText.error = massage
        editText.requestFocus()

    }


}