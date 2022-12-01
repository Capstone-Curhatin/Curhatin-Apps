package com.capstone.curhatin.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.core.ui.adapter.StoryPagingAdapter
import com.capstone.core.utils.MySharedPreference
import com.capstone.core.utils.navigateBack
import com.capstone.core.utils.navigateDirection
import com.capstone.curhatin.R
import com.capstone.curhatin.databinding.FragmentMyPostBinding
import com.capstone.curhatin.databinding.FragmentProfileBinding
import com.capstone.curhatin.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPostFragment : Fragment() {

    private var _binding: FragmentMyPostBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryViewModel by viewModels()
    private lateinit var mAdapter: StoryPagingAdapter

    @Inject lateinit var pref: MySharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.name.text = pref.getUser().name
        binding.tvEmail.text = pref.getUser().email
        binding.phone.text = pref.getUser().phone

        binding.backArrow.setOnClickListener { navigateBack() }

        setRecycler()
        loadState()
    }

    private fun setRecycler() {
        mAdapter = StoryPagingAdapter()
        binding.rvStory.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.getStoryByUser().observe(viewLifecycleOwner) { res ->
            mAdapter.submitData(lifecycle, res)
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