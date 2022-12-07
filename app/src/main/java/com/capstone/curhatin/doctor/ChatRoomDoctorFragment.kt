package com.capstone.curhatin.doctor

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import com.capstone.core.ui.chat.ChatRoomAdapter
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentChatRoomDoctorBinding
import com.capstone.curhatin.viewmodel.ChatDoctorViewModel
import com.capstone.curhatin.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class ChatRoomDoctorFragment : Fragment() {

    private var _binding: FragmentChatRoomDoctorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatDoctorViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    private val args: ChatRoomDoctorFragmentArgs by navArgs()
    @Inject lateinit var prefs: MySharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatRoomDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener { navigateBack() }

        // hide send button when the edittext is null
        binding.edtMessage.doOnTextChanged { text, _, _, _ ->
            if (text?.isEmpty() == true ||
                text?.isBlank() == true
            ) binding.ivSend.visibility = View.GONE
            else binding.ivSend.visibility = View.VISIBLE
        }

        readMessage()
        sendMessage()
        setHeader()
    }

    private fun readMessage(){
        val mAdapter = ChatRoomAdapter(prefs.getUser().id)
        binding.rvChat.apply {
            adapter = mAdapter
            val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            manager.stackFromEnd = true
            layoutManager = manager
            itemAnimator = DefaultItemAnimator()
        }

        val req = ReadMessageRequest(prefs.getUser().id, args.receiverId)
        viewModel.readMessage(req).observe(viewLifecycleOwner) { res ->
            when(res){
                is Resource.Loading -> {setLoading()}
                is Resource.Error -> {
                    stopLoading()
                    setDialogError(res.message.toString())
                }
                is Resource.Success -> {
                    stopLoading()
                    mAdapter.setData = res.data!!
                    if (res.data!!.isNotEmpty()) binding.rvChat.smoothScrollToPosition(res.data!!.size - 1)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage(){
        binding.ivSend.setOnClickListener {
            val currentDate = LocalDateTime.now().toString()
            val message = binding.edtMessage.getTextTrim()
            val request = ChatRoomDoctorRequest(
                sender_id = prefs.getUser().id, sender_name = prefs.getUser().name,
                sender_image = prefs.getUser().picture ?: Constant.ANONYMOUS_IMAGE,
                receiver_id = args.receiverId, receiver_name = args.receiverName.toString(),
                receiver_image = args.receiverImageUrl ?: Constant.ANONYMOUS_IMAGE,
                message = message, date = currentDate
            )

            binding.edtMessage.text.clear()

            viewModel.sendMessage(request).observe(viewLifecycleOwner){
                val name = prefs.getUser().name
                val send = SendNotificationRequest(args.receiverId, name, message)
                userViewModel.sendNotification(send)
            }

        }
    }

    private fun setHeader(){
        binding.tvName.text = args.receiverName
        binding.ivProfile.setImageUrl(args.receiverImageUrl ?: Constant.ANONYMOUS_IMAGE)
    }

}