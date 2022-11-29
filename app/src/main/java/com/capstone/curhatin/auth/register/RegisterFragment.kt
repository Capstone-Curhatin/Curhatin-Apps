package com.capstone.curhatin.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.RegisterRequest
import com.capstone.core.data.request.auth.VerifyOtpRequest
import com.capstone.core.utils.*
import com.capstone.curhatin.R
import com.capstone.curhatin.viewmodel.AuthViewModel
import com.capstone.curhatin.databinding.BottomSheetOtpBinding
import com.capstone.curhatin.databinding.FragmentRegisterBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var dispatchers: MyDispatchers
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnRegister.setOnClickListener { sendObservable() }
            loginUser.setOnClickListener { navigateBack() }
            registerDoctor.setOnClickListener { navigateDirection(
                RegisterFragmentDirections.actionRegisterFragmentToRegisterDoctorFragment()
            )}
        }
    }

    private fun sendObservable() {
        with(binding) {
            val name = etUsername.getTextTrim()
            val phone = etPhone.getTextTrim()
            val email = etEmail.getTextTrim()
            val password = etPassword.getTextTrim()
            val request = RegisterRequest(name = name, phone = phone, email = email, password = password, role = 0)

            viewModel.register(request).observe(viewLifecycleOwner){ res ->
                when(res){
                    is Resource.Loading -> {setLoading()}
                    is Resource.Error -> {
                        stopLoading()
                        setDialogError(res.message.toString())
                    }
                    is Resource.Success -> {
                        stopLoading()
                        showDialogOtp(email)
                    }
                }
            }
        }
    }

    private fun showDialogOtp(email: String){
        val bottomDialog = BottomSheetDialog(
            requireContext(), R.style.BottomSheetDialogTheme
        )

        val inflater = LayoutInflater.from(requireContext())
        val bindingOtp = BottomSheetOtpBinding.inflate(inflater)
        bottomDialog.setContentView(bindingOtp.root)
        bottomDialog.setCancelable(false)
        bottomDialog.show()

        bindingOtp.ivClose.setOnClickListener { bottomDialog.dismiss() }
        bindingOtp.pinView.doAfterTextChanged {
            /*
              check length of otp code and call viewmodel
              if otp code is validate, he can show the popup dialog
            */
            if (it?.count() == 4){
                val request = VerifyOtpRequest(email, it.toString().toInt())
                viewModel.userVerification(request).observe(viewLifecycleOwner){res ->
                    when(res){
                        is Resource.Loading -> {setLoading()}
                        is Resource.Error -> {
                            stopLoading()
                            setDialogError(res.message.toString())
                        }
                        is Resource.Success -> {
                            stopLoading()
                            setDialogSuccess(resources.getString(R.string.register_message_alert))
                            bottomDialog.dismiss()
                            navigateBack()
                        }
                    }
                }
            }
        }

        /*
        resend otp
        */
        bindingOtp.resendOtp.setOnClickListener {
            viewModel.requestOtp(email).observe(viewLifecycleOwner){ res ->
                when(res){
                    is Resource.Loading -> {setLoading()}
                    is Resource.Error -> {
                        stopLoading()
                        setDialogError(res.message.toString())
                    }
                    is Resource.Success -> {
                        stopLoading()
                        setDialogSuccess(res.data?.message.toString())
                    }
                }
            }
        }
    }
}



