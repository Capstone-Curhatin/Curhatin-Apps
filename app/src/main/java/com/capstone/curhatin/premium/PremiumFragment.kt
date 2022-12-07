package com.capstone.curhatin.premium

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.core.utils.navigateBack
import com.capstone.curhatin.R
import com.capstone.curhatin.databinding.FragmentPremiumBinding

class PremiumFragment : Fragment() {

    private var _binding: FragmentPremiumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPremiumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener { navigateBack() }
    }
}