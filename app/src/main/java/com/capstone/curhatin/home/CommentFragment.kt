package com.capstone.curhatin.home

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
import com.capstone.core.data.request.comment.CreateCommentRequest
import com.capstone.core.ui.adapter.CommentAdapter
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentCommentBinding
import com.capstone.curhatin.viewmodel.CommentViewModel
import com.capstone.curhatin.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

    private val args: CommentFragmentArgs by navArgs()
    private val viewModel: CommentViewModel by viewModels()
    private val storyViewModel: StoryViewModel by viewModels()

    private var isAnonymous = false
    private lateinit var mAdapter: CommentAdapter
    @Inject lateinit var prefs: MySharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // back to previous fragment
        binding.imgBack.setOnClickListener { navigateBack() }

        // set send icon
        binding.etComment.doOnTextChanged { text, _, _, _ ->
            if (text?.isEmpty() == true ||
                text?.isBlank() == true
            ) binding.ivSend.visibility = View.GONE
            else binding.ivSend.visibility = View.VISIBLE
        }

        // set anonymous comment
        isAnonymous = prefs.getAnonymous()
        binding.isAnonymous.isChecked = isAnonymous
        // get value from switch button
        binding.isAnonymous.setOnCheckedChangeListener { _, _ ->
            isAnonymous = !isAnonymous
        }

        binding.ivSend.setOnClickListener { sendComment() }

        setRecycler()
        readComments()
    }

    private fun readComments(){
        viewModel.getComments(args.id.toString()).observe(viewLifecycleOwner){ res ->
            when(res){
                is Resource.Loading -> {setLoading()}
                is Resource.Error -> {
                    stopLoading()
                    setDialogError(res.message.toString())
                }
                is Resource.Success -> {
                    stopLoading()
                    mAdapter.setData = res.data!!
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendComment(){
        val body = binding.etComment.getTextTrim()
        val request = CreateCommentRequest(
            id_story = args.id, id_user = prefs.getUser().id, anonymous = isAnonymous,
            image = prefs.getUser().picture.toString(), name = prefs.getUser().name, body = body, date = LocalDateTime.now().toString()
        )

        binding.etComment.text.clear()
        viewModel.createViewModel(request)
        storyViewModel.increment(args.id)

//        val notification = CreateNotificationRequest(
//            receiver_id = args.id, receiver_name = args.receiverName,
//            receiver_image = args.receiverImageUrl, anonymous = prefs.getAnonymous(),
//            body = Constant.NOTIFICATION_CHAT, date = LocalDateTime.now().toString(), type = Constant.TYPE_CHAT
//        )
    }

    private fun setRecycler(){
        mAdapter = CommentAdapter()
        binding.rvComments.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }
    }

}