package com.capstone.curhatin.auth.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.VerifyOtpRequest
import com.capstone.core.utils.*
import com.capstone.curhatin.R
import com.capstone.curhatin.databinding.BottomSheetOtpBinding
import com.capstone.curhatin.databinding.FragmentForgotPasswordBinding
import com.capstone.curhatin.viewmodel.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener { navigateBack() }
        binding.submitButton.setOnClickListener { sendObservable() }
    }

    private fun sendObservable() {
        with(binding) {
            val email = etEmail.getTextTrim()

            viewModel.requestOtp(email).observe(viewLifecycleOwner){ res ->
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
                            bottomDialog.dismiss()
                            navigateDirection(
                                ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToNewPasswordFragment(email)
                            )
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