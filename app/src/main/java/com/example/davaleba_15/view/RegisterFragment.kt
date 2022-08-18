package com.example.davaleba_15.view

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.davaleba_15.BaseFragment
import com.example.davaleba_15.R
import com.example.davaleba_15.ResponseState
import com.example.davaleba_15.databinding.FragmentRegisterBinding
import com.example.davaleba_15.model.TokenFResponse
import com.example.davaleba_15.model.UserFPost
import com.example.davaleba_15.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun init() {
        listeners()
    }

    private fun listeners() {
        binding.RegisterBtn.setOnClickListener {
            if (fieldsAreValid()) {
                val user = UserFPost(
                    binding.emailEditText.editText?.text.toString(),
                    binding.passwordEditText.editText?.text.toString()
                )
                viewModel.registerUser(user)
                handleResponse()
            }
        }
    }

    private fun handleResponse() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registerResponse.collect {
                binding.progressBar.isVisible = it.isLoading
                when (it) {
                    is ResponseState.Success -> handleSuccess(it.successData)
                    is ResponseState.Error -> handleError(it.errorMessage)
                    else -> {}
                }
            }
        }
    }

    private fun handleError(errorMessage: String?) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccess(body: TokenFResponse?) {
        Toast.makeText(requireContext(), body?.token, Toast.LENGTH_SHORT).show()
    }

    private fun fieldsAreValid(): Boolean {
        with(binding) {
            userEditText.error = null
            passwordEditText.error = null
            emailEditText.error = null
            when {
                userEditText.editText?.text.toString().isEmpty() -> {
                    userEditText.error = "This field is empty!"
                    return false
                }

                passwordEditText.editText?.text.toString().isEmpty() -> {
                    passwordEditText.error = "This field is empty!"
                    return false
                }

                emailEditText.editText?.text.toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(
                    emailEditText.editText?.text.toString()
                ).matches() -> {
                    emailEditText.error = "This field is invalid!"
                    return false
                }
                else -> {
                    return true
                }
            }
        }
    }
}