package com.nurbk.ps.hashitaqalquds.ui.fragment.auth

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentSignInBinding
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignInFragment : Fragment() {

    @Inject
    lateinit var viewModel: AuthViewModel
    private val dialog by lazy {
        Dialog(requireContext())
    }

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

        createDialog()

        viewModel.signInGetLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.EMPTY -> {
                }
                Result.Status.ERROR -> {
                    dialog.dismiss()
                }
                Result.Status.LOADING -> {
                    if (!dialog.isShowing)
                        dialog.show()
                }
                Result.Status.SUCCESS -> {
                    dialog.dismiss()
                    findNavController().navigate(R.id.action_LoginFragment_to_homeFragment)
                }
            }

        }
        mBinding.btnSignIn.setOnClickListener {
            validate()
        }

        mBinding.createSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_signUpFragment)
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

    private fun createDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)

    }

}