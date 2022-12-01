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
import com.capstone.curhatin.auth.AuthActivity
import com.capstone.curhatin.databinding.FragmentProfileBinding
import com.capstone.curhatin.viewmodel.AuthViewModel
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

        binding.myPost.setOnClickListener{ navigateDirection(ProfileFragmentDirections.actionProfileFragmentToMyPostFragment())}

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

        // get value from switch button
        binding.isAnonymous.setOnCheckedChangeListener { _, _ ->

            builder.setTitle("Set Anonymously")
                .setMessage("Are you sure want to set anonymously?")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->
                    pref.setAnonymous(!pref.getAnonymous())
                }
                .setNegativeButton("No") { dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }
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

}