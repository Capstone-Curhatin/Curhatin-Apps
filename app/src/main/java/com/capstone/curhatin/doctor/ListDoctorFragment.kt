package com.capstone.curhatin.doctor

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.core.data.common.Resource
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.domain.model.User
import com.capstone.core.ui.chat.DoctorAdapter
import com.capstone.core.utils.*

import com.capstone.curhatin.R
import com.capstone.curhatin.databinding.FragmentDoctorChatBinding

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
    private val listUser = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            filter(text.toString())
        }

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

        mAdapter.setOnDetailItemClick { user ->
            navigateDirection(
                ListDoctorFragmentDirections.actionListDoctorFragmentToProfileDoctorFragment(user.id)
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
                    listUser.clear()
                    listUser.addAll(res.data?.data!!)

                    if (listUser.isEmpty()){
                        binding.lottieEmpty.visibility = View.VISIBLE
                        binding.lottieNotFound.visibility = View.GONE
                        binding.rvDoctor.visibility = View.GONE
                    }else{
                        binding.lottieEmpty.visibility = View.GONE
                        binding.lottieNotFound.visibility = View.GONE
                        binding.rvDoctor.visibility = View.VISIBLE
                        mAdapter.setData = listUser
                    }
                }
            }
        }

    }

    private fun filter(text: String){
        val filtered = ArrayList<User>()
        for (item in listUser){
            if (item.name.lowercase().contains(text.lowercase())){
                filtered.add(item)
            }
        }

        if (filtered.isEmpty()){
            binding.lottieEmpty.visibility = View.GONE
            binding.lottieNotFound.visibility = View.VISIBLE
            binding.rvDoctor.visibility = View.GONE
        }else{
            binding.lottieEmpty.visibility = View.GONE
            binding.lottieNotFound.visibility = View.GONE
            binding.rvDoctor.visibility = View.VISIBLE
            mAdapter.setData = filtered
        }
    }

    override fun onStop() {
        super.onStop()
        binding.edtSearch.text.clear()
    }


}