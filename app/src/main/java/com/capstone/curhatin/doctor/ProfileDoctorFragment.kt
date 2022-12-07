package com.capstone.curhatin.doctor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.capstone.core.data.common.Resource
import com.capstone.core.domain.model.Doctor
import com.capstone.core.domain.model.User
import com.capstone.core.utils.*
import com.capstone.curhatin.R
import com.capstone.curhatin.auth.AuthActivity
import com.capstone.curhatin.databinding.FragmentListDoctorBinding
import com.capstone.curhatin.databinding.FragmentProfileBinding
import com.capstone.curhatin.databinding.FragmentProfileDoctorBinding
import com.capstone.curhatin.home.CommentFragmentArgs
import com.capstone.curhatin.viewmodel.AuthViewModel
import com.capstone.curhatin.viewmodel.DoctorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileDoctorFragment : Fragment() {

    private var _binding: FragmentProfileDoctorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoctorViewModel by viewModels()
    private val args: ProfileDoctorFragmentArgs by navArgs()

    @Inject
    lateinit var prefs: MySharedPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDetail()

        binding.backArrow.setOnClickListener { navigateBack() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getDetail() {
        viewModel.detailDoctor(args.id).observe(viewLifecycleOwner){ res ->
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


                    //res.data?.data
                }
            }
        }
    }



    }

