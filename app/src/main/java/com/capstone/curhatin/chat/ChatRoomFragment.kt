package com.capstone.curhatin.chat

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.capstone.core.data.request.chat.ChatRoomRequest
import com.capstone.core.utils.MySharedPreference
import com.capstone.core.utils.getTextTrim
import com.capstone.core.utils.navigateBack
import com.capstone.curhatin.databinding.FragmentChatRoomBinding
import com.capstone.curhatin.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class ChatRoomFragment : Fragment() {

    private var _binding: FragmentChatRoomBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()
    private val args: ChatRoomFragmentArgs by navArgs()
    @Inject lateinit var prefs: MySharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener { navigateBack() }

        setHeader()
        // function for send message
        sendMessage()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage(){
        binding.ivSend.setOnClickListener {
            val currentDate = LocalDateTime.now().toString()
            val message = binding.edtMessage.getTextTrim()
            val request = ChatRoomRequest(
                sender_id = prefs.getUser().id,
                receiver_id = args.receiverId,
                message = message,
                date = currentDate
            )

            viewModel.sendMessage(request).observe(viewLifecycleOwner){
                binding.edtMessage.text.clear()
            }
        }
    }

    private fun setHeader(){
        binding.tvName.text = args.receiverName
    }

}