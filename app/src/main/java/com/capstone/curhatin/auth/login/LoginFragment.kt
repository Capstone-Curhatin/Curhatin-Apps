package com.capstone.curhatin.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.domain.model.User
import com.capstone.core.utils.*
import com.capstone.curhatin.MainActivity
import com.capstone.curhatin.viewmodel.AuthViewModel
import com.capstone.curhatin.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var prefs: MySharedPreference
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

        binding.apply {
            btnLogin.setOnClickListener { sendObservable() }
            register.setOnClickListener { navigateDirection(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )}
            tvForgetPassword.setOnClickListener { navigateDirection(
                LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            )}
        }
    }

    private fun sendObservable() {
        with(binding) {
            val email = etEmail.getTextTrim()
            val password = etPassword.getTextTrim()
            val request = LoginRequest(email, password)

            viewModel.login(request).observe(viewLifecycleOwner){ res ->
                val data = res.data?.data
                when(res){
                    is Resource.Loading -> {setLoading()}
                    is Resource.Error -> {
                        stopLoading()
                        setDialogError(res.message.toString())
                    }
                    is Resource.Success -> {
                        stopLoading()
                        prefs.setLogin(true)
                        prefs.setToken(data?.access_token.toString())

                        val user = data?.user
                        val picture = user?.picture ?: Constant.ANONYMOUS_IMAGE
                        val data = User(
                            id = user!!.id, name = user.name, email = user.email, phone = user.phone,
                            picture = picture, role = user.role, otp = user.otp, is_premium = user.is_premium
                        )
                        prefs.setUser(data)

                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                }
            }
        }
    }
}