package com.capstone.curhatin.chat

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
import com.capstone.core.data.request.chat.ChatRoomRequest
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.request.notification.CreateNotificationRequest
import com.capstone.core.ui.chat.ChatRoomAdapter
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentChatRoomBinding
import com.capstone.curhatin.viewmodel.ChatViewModel
import com.capstone.curhatin.viewmodel.NotificationViewModel
import com.capstone.curhatin.viewmodel.UserViewModel
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class ChatRoomFragment : Fragment() {

    private var _binding: FragmentChatRoomBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()

    private val args: ChatRoomFragmentArgs by navArgs()
    @Inject lateinit var prefs: MySharedPreference

    // set read chat from fragment
    private lateinit var stateListener: ValueEventListener
    private lateinit var resetStateListener: ValueEventListener

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

        // hide send button when the edittext is null
        binding.edtMessage.doOnTextChanged { text, _, _, _ ->
            if (text?.isEmpty() == true ||
                text?.isBlank() == true
            ) binding.ivSend.visibility = View.GONE
            else binding.ivSend.visibility = View.VISIBLE
        }

        readMessage()
        setHeader()
        sendMessage()
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
                    Timber.d("Data: ${res.data}")
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
            val request = ChatRoomRequest(
                sender_id = prefs.getUser().id,
                receiver_id = args.receiverId,
                message = message,
                date = currentDate
            )

            binding.edtMessage.text.clear()

            viewModel.sendMessage(request).observe(viewLifecycleOwner){
                val name = if (args.anonymous) Constant.ANONYMOUS else prefs.getUser().name
                val send = SendNotificationRequest(args.receiverId, name, message)
                userViewModel.sendNotification(send)
            }

            val notification = CreateNotificationRequest(
                receiver_id = args.receiverId, receiver_name = args.receiverName,
                receiver_image = args.receiverImageUrl, anonymous = prefs.getAnonymous(),
                body = Constant.NOTIFICATION_CHAT, date = LocalDateTime.now().toString(), type = Constant.TYPE_CHAT
            )
            notificationViewModel.createStory(notification)
        }
    }

    private fun setHeader(){
        if (args.anonymous){
            binding.tvName.text = Constant.ANONYMOUS
            binding.ivProfile.setImageUrl(Constant.ANONYMOUS_IMAGE)
        }else{
            binding.tvName.text = args.receiverName
            binding.ivProfile.setImageUrl(args.receiverImageUrl.toString())
        }
    }


    // set read status and reset unread count
    private val dbReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(Endpoints.CHAT)
    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
        stateListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    i.child("read").ref.setValue(true)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        }

        dbReference.child(args.receiverId.toString()).child(prefs.getUser().id.toString())
            .child(Endpoints.CHAT_ROOM).addValueEventListener(stateListener)

        resetStateListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.child("unread").ref.setValue(0)
            }
            override fun onCancelled(error: DatabaseError) {}
        }

        dbReference.child(prefs.getUser().id.toString()).child(args.receiverId.toString())
            .addValueEventListener(resetStateListener)
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
        dbReference.child(args.receiverId.toString()).child(prefs.getUser().id.toString())
            .child(Endpoints.CHAT_ROOM).removeEventListener(stateListener)

        dbReference.child(prefs.getUser().id.toString()).child(args.receiverId.toString())
            .removeEventListener(resetStateListener)
    }

}