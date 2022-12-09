package com.capstone.curhatin.doctor

import android.os.Bundle
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
import com.capstone.core.ui.chat.ChatUserAdapter
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentDoctorChatBinding
import com.capstone.curhatin.viewmodel.ChatDoctorViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DoctorChatFragment : Fragment() {

    private var _binding: FragmentDoctorChatBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var pref: MySharedPreference

    private val viewModel: ChatDoctorViewModel by viewModels()
    private lateinit var mAdapter: ChatUserAdapter
    private val listChat = ArrayList<ChatUserResponse>()

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
        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            if (text?.isNotEmpty() == true){
                filter(text.toString())
            }
        }

        mAdapter = ChatUserAdapter()
        binding.rvChat.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        mAdapter.setOnItemClick { user ->
            navigateDirection(
                DoctorChatFragmentDirections.actionDoctorChatFragmentToChatRoomDoctorFragment(
                    user.id!!, user.name, user.image_url
                )
            )
        }

        checkRole()
    }

    private fun checkRole(){
        if (pref.getUser().role == 1){
            binding.linearSearch.visibility = View.VISIBLE
            binding.rvChat.visibility = View.VISIBLE
            binding.constraintPremium.visibility = View.GONE
            binding.imgAdd.visibility = View.GONE
            observable()
        }else checkPremium()
    }

    private fun checkPremium() {
        if (pref.getUser().is_premium) {
            binding.linearSearch.visibility = View.VISIBLE
            binding.rvChat.visibility = View.VISIBLE
            binding.constraintPremium.visibility = View.GONE
            binding.lottieEmpty.visibility = View.GONE
            binding.lottieNotFound.visibility = View.GONE

            observable()
        } else {
            binding.linearSearch.visibility = View.GONE
            binding.rvChat.visibility = View.GONE
            binding.constraintPremium.visibility = View.VISIBLE
            binding.lottieEmpty.visibility = View.GONE
            binding.lottieNotFound.visibility = View.GONE
        }
    }


    private fun observable(){
        viewModel.getUserMessage(pref.getUser().id.toString()).observe(viewLifecycleOwner){ res ->
            when(res){
                is Resource.Loading -> {setLoading()}
                is Resource.Error -> {
                    stopLoading()
                    setDialogError(res.message.toString())
                }
                is Resource.Success -> {
                    stopLoading()
                    listChat.clear()
                    listChat.addAll(res.data!!)

                    if (listChat.isEmpty()){

                        binding.lottieEmpty.visibility = View.VISIBLE
                        binding.lottieNotFound.visibility = View.GONE
                        binding.rvChat.visibility = View.GONE
                        binding.constraintPremium.visibility = View.GONE
                    }else{
                        binding.constraintPremium.visibility = View.GONE
                        binding.lottieEmpty.visibility = View.GONE
                        binding.lottieNotFound.visibility = View.GONE
                        binding.rvChat.visibility = View.VISIBLE
                        mAdapter.setData = listChat
                    }
                }
            }
        }
    }

    private fun filter(text: String){
        val filtered = ArrayList<ChatUserResponse>()
        for (item in listChat){
            if (item.name?.lowercase()?.contains(text.lowercase()) == true){
                filtered.add(item)
            }
        }

        if (filtered.isEmpty()){
            binding.constraintPremium.visibility = View.GONE
            binding.lottieEmpty.visibility = View.GONE
            binding.lottieNotFound.visibility = View.VISIBLE
            binding.rvChat.visibility = View.GONE
        }else{
            binding.constraintPremium.visibility = View.GONE
            binding.lottieEmpty.visibility = View.GONE
            binding.lottieNotFound.visibility = View.GONE
            binding.rvChat.visibility = View.VISIBLE
            mAdapter.setData = filtered
        }
    }

    override fun onStop() {
        super.onStop()
        binding.edtSearch.text.clear()
    }

}