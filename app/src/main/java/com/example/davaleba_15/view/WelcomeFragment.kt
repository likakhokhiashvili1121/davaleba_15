package com.example.davaleba_15.view


import androidx.navigation.fragment.findNavController
import com.example.davaleba_15.BaseFragment
import com.example.davaleba_15.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    override fun init() {
        binding.loginBtn.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment())
        }
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment())
        }
    }

}