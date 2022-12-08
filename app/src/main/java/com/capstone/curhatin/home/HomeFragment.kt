package com.capstone.curhatin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.core.data.common.Resource
import com.capstone.core.domain.model.Category
import com.capstone.core.ui.adapter.CategoryAdapter
import com.capstone.core.ui.adapter.StoryLoadStateAdapter
import com.capstone.core.ui.adapter.StoryPagingAdapter
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentHomeBinding
import com.capstone.curhatin.viewmodel.CategoryViewModel
import com.capstone.curhatin.viewmodel.NotificationViewModel
import com.capstone.curhatin.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryViewModel by viewModels()
    private val notifViewModel: NotificationViewModel by viewModels()
    private lateinit var mAdapter: StoryPagingAdapter

    @Inject lateinit var prefs: MySharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvAddStory.setOnClickListener {
            navigateDirection(HomeFragmentDirections.actionHomeFragmentToCreateStoryFragment())
        }

        binding.imgPicture.setImageUrl(prefs.getUser().picture.toString())
        binding.ivNotification.setOnClickListener {
            navigateDirection(HomeFragmentDirections.actionHomeFragmentToNotificationFragment())
        }

        setRecycler()
        loadState()
        setCountNotification()
    }

    private fun setRecycler() {
        mAdapter = StoryPagingAdapter()
        binding.rvStory.apply {
            adapter = mAdapter.withLoadStateHeaderAndFooter(
                footer = StoryLoadStateAdapter {mAdapter.retry()},
                header = StoryLoadStateAdapter {mAdapter.retry()}
            )
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.getStories().observe(viewLifecycleOwner) { res ->
            mAdapter.submitData(lifecycle, res)
        }   

        mAdapter.setOnClickListener {
            navigateDirection(
                HomeFragmentDirections.actionHomeFragmentToCommentFragment(it)
            )
        }
    }

    private fun setCountNotification(){
        notifViewModel.getCountNotification(prefs.getUser().id.toString()).observe(viewLifecycleOwner){ res ->
            if (res is Resource.Success){
                Timber.d("Total notification: ${res.data}")
            }
        }
    }

    private fun loadState() {
        mAdapter.addLoadStateListener { loadState ->
            binding.apply {
                rvStory.isVisible = loadState.source.refresh is LoadState.NotLoading
                lottieLoading.isVisible = loadState.source.refresh is LoadState.Loading
                rvStory.isVisible =
                    !(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && mAdapter.itemCount < 1)
            }
        }
    }
}