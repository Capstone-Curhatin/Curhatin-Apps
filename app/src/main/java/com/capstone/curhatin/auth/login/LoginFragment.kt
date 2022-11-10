package com.capstone.curhatin.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.utils.MySharedPreference
import com.capstone.curhatin.auth.AuthViewModel
import com.capstone.curhatin.databinding.FragmentLoginBinding
import com.capstone.core.utils.getTextTrim
import com.capstone.core.utils.quickShowToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var prefs: MySharedPreference
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener { sendObservable() }
    }

    private fun sendObservable(){
        with(binding){
            val email = etEmail.getTextTrim()
            val password = etPassword.getTextTrim()
            val request = LoginRequest(email, password)

            viewModel.login(request)
            viewModel.error.observe(viewLifecycleOwner){
                quickShowToast(it)
            }
            viewModel.login.observe(viewLifecycleOwner){
                Timber.d("USER DATA: $it")
            }

        }
    }

}