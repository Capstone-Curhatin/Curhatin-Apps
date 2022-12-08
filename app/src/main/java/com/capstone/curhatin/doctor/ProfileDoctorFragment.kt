package com.capstone.curhatin.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.capstone.core.data.common.Resource
import com.capstone.core.domain.model.User
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentProfileDoctorBinding
import com.capstone.curhatin.viewmodel.DoctorViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileDoctorFragment : Fragment() {

    private var _binding: FragmentProfileDoctorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoctorViewModel by viewModels()
    private val args: ProfileDoctorFragmentArgs by navArgs()

    @Inject
    lateinit var prefs: MySharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.backArrow.setOnClickListener { navigateBack() }
        getDetail()
    }

    private fun getDetail() {
        viewModel.detailDoctor(args.id).observe(viewLifecycleOwner) { res ->
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
                    val data = res.data?.data
                    if (data != null) setView(data)

                }
            }
        }
    }

    private fun setView(data: User){
        with(binding){
            ivPhoto.setImageUrl(data.picture ?: Constant.ANONYMOUS_IMAGE)
            tvName.text = data.name
            tvSpecialist.text = data.doctor?.specialist
            jobExperience.text = "${data.doctor?.experience} years"
            tvStr.text = data.doctor?.str_number


        }
    }
}






