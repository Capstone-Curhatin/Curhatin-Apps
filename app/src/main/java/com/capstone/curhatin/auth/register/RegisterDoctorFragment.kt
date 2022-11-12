package com.capstone.curhatin.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.core.utils.navigateBack
import com.capstone.core.utils.navigateDirection
import com.capstone.curhatin.R
import com.capstone.curhatin.databinding.FragmentRegisterDoctorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterDoctorFragment : Fragment() {

    private var _binding: FragmentRegisterDoctorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            registerUser.setOnClickListener { navigateBack() }
            loginUser.setOnClickListener { navigateDirection(
                RegisterFragmentDirections.actionRegisterFragmentToRegisterDoctorFragment()
            )}
        }

    }

}