package com.capstone.curhatin.chat

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.utils.*
import com.capstone.curhatin.R
import com.capstone.curhatin.databinding.BottomSheetModeBinding
import com.capstone.curhatin.databinding.FragmentChatBinding
import com.capstone.curhatin.viewmodel.WaitingRoomViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject


@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val modeViewModel: WaitingRoomViewModel by viewModels()

    @Inject lateinit var prefs: MySharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgAdd.setOnClickListener { showBottomMode() }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showBottomMode(){
        val bottomDialog = BottomSheetDialog(
            requireContext(), R.style.BottomSheetDialogTheme
        )

        val inflater = LayoutInflater.from(requireContext())
        val bindingMode = BottomSheetModeBinding.inflate(inflater)
        bottomDialog.setContentView(bindingMode.root)
        bottomDialog.setCancelable(false)
        bottomDialog.show()

        bindingMode.btnClose.setOnClickListener {
            bottomDialog.dismiss()
        }

        bindingMode.btnListen.setOnClickListener {
            val currentDate = LocalDateTime.now().toString()
            Timber.d("CURRENT TIME: $currentDate")
            val request = WaitingRoomRequest(user_id = prefs.getUser().id, date = currentDate)
            modeViewModel.createWaitingRoom(request).observe(viewLifecycleOwner){ res ->
                when(res){
                    is Resource.Loading -> {setLoading()}
                    is Resource.Error -> {
                        stopLoading()
                        setDialogError(res.message.toString())
                    }
                    is Resource.Success -> {
                        stopLoading()
                        setDialogSuccess(res.data?.message.toString())
                        bottomDialog.dismiss()
                    }
                }
            }
        }

    }
}