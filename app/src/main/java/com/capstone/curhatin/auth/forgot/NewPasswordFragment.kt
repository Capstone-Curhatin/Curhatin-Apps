package com.capstone.curhatin.auth.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.PasswordRequest
import com.capstone.core.utils.*
import com.capstone.curhatin.R
import com.capstone.curhatin.databinding.FragmentNewPasswordBinding
import com.capstone.curhatin.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordFragment : Fragment() {

    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()
    private val args : NewPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener { navigateBack() }
        binding.changePasswordButton.setOnClickListener { sendObservable() }
    }

    private fun sendObservable() {
        with(binding) {
            val email = args.email
            val newPassword = etNewPassword.getTextTrim()
            val confirmPassword = etConfirmPassword.getTextTrim()

            if( newPassword == confirmPassword ){
                val request = PasswordRequest(email = email, password = newPassword)

                viewModel.updatePassword(request).observe(viewLifecycleOwner){ res ->
                    when(res){
                        is Resource.Loading -> {setLoading()}
                        is Resource.Error -> {
                            stopLoading()
                            setDialogError(res.message.toString())
                        }
                        is Resource.Success -> {
                            stopLoading()
                            setDialogSuccess(resources.getString(R.string.update_password_message_alert))
                            navigateDirection(
                                NewPasswordFragmentDirections.actionNewPasswordFragmentToLoginFragment()
                            )
                        }
                    }
                }
            } else {
                setDialogError(resources.getString(R.string.different_password_message_alert))
            }
        }
    }
}