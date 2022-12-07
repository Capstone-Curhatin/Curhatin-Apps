package com.capstone.curhatin.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.core.utils.MySharedPreference
import com.capstone.core.utils.navigateDirection
import com.capstone.curhatin.databinding.FragmentDoctorChatBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DoctorChatFragment : Fragment() {

    private var _binding: FragmentDoctorChatBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var pref: MySharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgAdd.setOnClickListener { navigateDirection(DoctorChatFragmentDirections.actionDoctorChatFragmentToListDoctorFragment()) }

        checkRole()
    }

    private fun checkRole(){
        if (pref.getUser().role == 1){
            binding.linearSearch.visibility = View.VISIBLE
            binding.rvChat.visibility = View.VISIBLE
            binding.constraintPremium.visibility = View.GONE
            binding.imgAdd.visibility = View.GONE
        }else checkPremium()
    }

    private fun checkPremium() {
        if (pref.getUser().is_premium) {
            binding.linearSearch.visibility = View.VISIBLE
            binding.rvChat.visibility = View.VISIBLE
            binding.constraintPremium.visibility = View.GONE
        } else {
            binding.linearSearch.visibility = View.GONE
            binding.rvChat.visibility = View.GONE
            binding.constraintPremium.visibility = View.VISIBLE
        }
    }

}