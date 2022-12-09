package com.capstone.curhatin.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.core.data.common.Resource
import com.capstone.core.utils.*
import com.capstone.curhatin.R
import com.capstone.curhatin.auth.AuthActivity
import com.capstone.curhatin.auth.forgot.ForgotPasswordFragment
import com.capstone.curhatin.databinding.FragmentProfileBinding
import com.capstone.curhatin.viewmodel.AuthViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var builder: AlertDialog.Builder

    @Inject
    lateinit var pref: MySharedPreference
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.name.text = pref.getUser().name
        binding.tvEmail.text = pref.getUser().email
        binding.phone.text = pref.getUser().phone
        binding.isAnonymous.isChecked = pref.getAnonymous()
        binding.circleImageView.setImageUrl(pref.getUser().picture.toString())

        binding.myPost.setOnClickListener{ navigateDirection(ProfileFragmentDirections.actionProfileFragmentToMyPostFragment())}
        binding.getPremium.setOnClickListener{ navigateDirection(ProfileFragmentDirections.actionProfileFragmentToPremiumFragment())}
        binding.editProfileTitle.setOnClickListener { navigateDirection(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()) }
        binding.privacyPolicy.setOnClickListener { toPrivacyPolicy() }
        binding.termsCondition.setOnClickListener { toTermsAndConditions() }

        binding.isAnonymous.setOnCheckedChangeListener { _ , isChecked  ->
            if (isChecked) {
                pref.setAnonymous(true)
            } else {
                pref.setAnonymous(false)
            }
        }

        builder = AlertDialog.Builder(requireActivity())
        binding.logout.setOnClickListener {
            builder.setTitle("Log Out")
                .setMessage("Are you sure want to Log Out?")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->
                    logout()
                }
                .setNegativeButton("No") { dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }

        checkRole()
    }

    private fun logout() = lifecycleScope.launch {
        viewModel.logout().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    setLoading()
                }
                is Resource.Error -> {
                    stopLoading()
                    setDialogError(res.message.toString())
                }
                is Resource.Success -> {
                    stopLoading()
                    pref.logout()

                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }

    private fun checkRole(){
        if (pref.getUser().role == 1){
            binding.roleBasic.visibility = View.GONE
            binding.rolePremium.visibility = View.GONE
            binding.roleDoctor.visibility = View.VISIBLE
        }else checkPremium()
    }

    private fun checkPremium() {
        if (pref.getUser().is_premium) {
            binding.roleBasic.visibility = View.GONE
            binding.rolePremium.visibility = View.VISIBLE
            binding.roleDoctor.visibility = View.GONE
        } else {
            binding.roleBasic.visibility = View.VISIBLE
            binding.rolePremium.visibility = View.GONE
            binding.roleDoctor.visibility = View.GONE
        }
    }
}