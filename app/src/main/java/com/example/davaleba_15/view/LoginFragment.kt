package com.example.davaleba_15.view


import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.davaleba_15.BaseFragment
import com.example.davaleba_15.ResponseState
import com.example.davaleba_15.databinding.FragmentLoginBinding
import com.example.davaleba_15.model.TokenFResponse
import com.example.davaleba_15.model.UserFPost
import com.example.davaleba_15.viewmodel.LogInViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LogInViewModel by viewModels()
    override fun init() {
        listeners()
    }

    private fun listeners() {
        binding.loginBtn.setOnClickListener {
            if (fieldsAreValid()) {
                val user = UserFPost(
                    binding.emailET.editText?.text.toString(),
                    binding.passwordET.editText?.text.toString()
                )
                viewModel.loginUser(user)
                handleResponse()
            }
        }
    }

    private fun handleResponse(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.loginResponse.collect{
                binding.progressBar.isVisible = it.isLoading
                when(it){
                    is ResponseState.Success -> handleSuccess(it.successData)
                    is ResponseState.Error -> handleError(it.errorMessage)
                    else -> { }
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
            emailET.error = null
            passwordET.error = null
            when {
                emailET.editText?.text.toString().isEmpty() -> {
                    emailET.error = "This field is empty!"
                    return false
                }

                passwordET.editText?.text.toString().isEmpty() -> {
                    passwordET.error = "This field is empty!"
                    return false
                }
                else -> {
                    return true
                }
            }
        }
    }
}