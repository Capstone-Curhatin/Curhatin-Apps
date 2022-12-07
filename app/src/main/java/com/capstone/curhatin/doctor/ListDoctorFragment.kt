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
import com.capstone.core.ui.chat.DoctorAdapter
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentListDoctorBinding
import com.capstone.curhatin.viewmodel.DoctorViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListDoctorFragment : Fragment() {

    private var _binding: FragmentListDoctorBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var pref: MySharedPreference
    private val viewModel: DoctorViewModel by viewModels()

    private lateinit var mAdapter: DoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycler()
    }

    private fun setRecycler() {
        mAdapter = DoctorAdapter()
        binding.rvDoctor.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        mAdapter.setOnItemClick { user ->
            navigateDirection(
                ListDoctorFragmentDirections.actionListDoctorFragmentToChatRoomDoctorFragment(
                    user.id, user.name, user.picture
                )
            )
        }

        viewModel.getDoctor().observe(viewLifecycleOwner) {res ->
            when(res){
                is Resource.Loading -> {setLoading()}
                is Resource.Error -> {
                    stopLoading()
                    setDialogError(res.message.toString())
                }
                is Resource.Success -> {
                    stopLoading()
                    mAdapter.setData = res.data?.data!!
                }
            }
        }
    }

}