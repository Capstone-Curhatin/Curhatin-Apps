package com.capstone.curhatin.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.core.data.common.Resource
import com.capstone.core.utils.MySharedPreference
import com.capstone.core.utils.setDialogError
import com.capstone.core.utils.setLoading
import com.capstone.core.utils.stopLoading
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

        binding.logout.setOnClickListener {
            logout()
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