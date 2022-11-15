package com.capstone.curhatin.auth.register

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import com.capstone.curhatin.R
import com.google.android.material.bottomsheet.BottomSheetDialog

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.core.data.common.DialogType
import com.capstone.core.data.request.auth.RegisterRequest
import com.capstone.core.ui.dialog.PopupDialog
import com.capstone.core.utils.*
import com.capstone.curhatin.auth.AuthViewModel
import com.capstone.curhatin.databinding.BottomSheetOtpBinding
import com.capstone.curhatin.databinding.CustomDialogRegisterBinding
import com.capstone.curhatin.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

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

            viewModel.register(request)
            viewModel.error.observe(viewLifecycleOwner){
                setDialogError(it)
            }
            viewModel.register.observe(viewLifecycleOwner) {
                showDialogOtp(email)
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
               setDialogSuccess("Berhasil mendaftar!")
            }
        }
    }

}



