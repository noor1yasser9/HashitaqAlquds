package com.nurbk.ps.hashitaqalquds.ui.fragment.auth

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.FragmentSignUpBinding
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.util.Result
import com.nurbk.ps.hashitaqalquds.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {


    @Inject
    lateinit var viewModel: AuthViewModel
    private val dialog by lazy {
        Dialog(requireContext())
    }
    private val mBinding by lazy {
        FragmentSignUpBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createDialog()


        mBinding.txtSignIn.setOnClickListener {
            findNavController().popBackStack()
        }

        mBinding.btnSignUp.setOnClickListener {
            createAccount()
        }

        viewModel.signUpGetLiveData.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    dialog.dismiss()
                    findNavController().navigateUp()
                }
                Result.Status.LOADING -> {
                    dialog.show()
                }
                Result.Status.ERROR -> {
                    dialog.dismiss()
                    Snackbar.make(requireView(), result.message!!, Snackbar.LENGTH_LONG).show()
                }
                Result.Status.EMPTY -> {
                }
            }
        }
    }


    private fun createAccount() {
        val name: String = mBinding.txtName.editText!!.text.toString()
        val email: String = mBinding.txtEmail.editText!!.text.toString().trim()
        val phone: String = mBinding.txtPhone.editText!!.text.toString()
        val password: String = mBinding.txtPassword.editText!!.text.toString()
        if (TextUtils.isEmpty(name)) {
            errorText(mBinding.txtName.editText!!, getString(R.string.field_required))
        } else if (TextUtils.isEmpty(email)) {
            errorText(mBinding.txtEmail.editText!!, getString(R.string.field_required))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorText(mBinding.txtEmail.editText!!, getString(R.string.error_email))
        } else if (TextUtils.isEmpty(phone)) {
            errorText(mBinding.txtPhone.editText!!, getString(R.string.field_required))
        } else if (Pattern.matches("[a-zA-Z-+]+", phone)
            || phone.length != 12
        ) {
            errorText(mBinding.txtPhone.editText!!, getString(R.string.error_phone))
        } else if (TextUtils.isEmpty(password)) {
            errorText(mBinding.txtPassword.editText!!, getString(R.string.field_required))
        } else if (password.length < 6) {
            errorText(mBinding.txtPassword.editText!!, getString(R.string.password_error))
        } else {
            val users = User(
                name = name, email = email, id = UUID.randomUUID().toString(),
                phone = phone
            )
            viewModel.createAccount(users, password)
        }
    }

    private fun errorText(text: EditText, message: String) {
        text.error = message
        text.requestFocus()
    }

    private fun createDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)

    }
}