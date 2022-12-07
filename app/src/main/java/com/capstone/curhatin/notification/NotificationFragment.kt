package com.capstone.curhatin.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.core.data.common.Resource
import com.capstone.core.ui.adapter.NotificationAdapter
import com.capstone.core.utils.MySharedPreference
import com.capstone.core.utils.setDialogError
import com.capstone.core.utils.setLoading
import com.capstone.core.utils.stopLoading
import com.capstone.curhatin.databinding.FragmentNotificationBinding
import com.capstone.curhatin.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAdapter: NotificationAdapter
    @Inject lateinit var prefs: MySharedPreference

    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = NotificationAdapter()
        binding.rvNotification.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        sendObserver()
    }

    private fun sendObserver(){
        viewModel.getNotification(prefs.getUser().id.toString()).observe(viewLifecycleOwner) {res ->
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

}