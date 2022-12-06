package com.capstone.curhatin.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.core.data.common.Resource
import com.capstone.core.ui.chat.ChatUserAdapter
import com.capstone.core.ui.chat.DoctorAdapter
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentDoctorChatBinding
import com.capstone.curhatin.viewmodel.DoctorViewModel
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
    ): View? {
        _binding = FragmentDoctorChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgAdd.setOnClickListener { navigateDirection(DoctorChatFragmentDirections.actionDoctorChatFragmentToListDoctorFragment()) }

        checkPremium()
    }

    private fun checkPremium() {
        if (pref.getUser().is_premium == false) {
            binding.rvChatDoctor.visibility = View.GONE
            binding.constraintPremium.visibility = View.VISIBLE
        } else {
            binding.rvChatDoctor.visibility = View.VISIBLE
            binding.constraintPremium.visibility = View.GONE

        }
    }
}