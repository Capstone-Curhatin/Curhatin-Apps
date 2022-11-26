package com.capstone.curhatin.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.core.data.common.Resource
import com.capstone.core.domain.model.Category
import com.capstone.core.ui.adapter.CategoryAdapter
import com.capstone.core.ui.adapter.StoryPagingAdapter
import com.capstone.core.utils.*
import com.capstone.curhatin.auth.AuthActivity
import com.capstone.curhatin.databinding.FragmentHomeBinding
import com.capstone.curhatin.viewmodel.CategoryViewModel
import com.capstone.curhatin.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var mAdapter: StoryPagingAdapter
    private lateinit var cAdapter: CategoryAdapter
    private val listCategory = ArrayList<Category>()

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

        setRecycler()
        loadState()
    }

    private fun setRecycler(){
        mAdapter = StoryPagingAdapter()
        binding.rvStory.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.getStories().observe(viewLifecycleOwner){ res ->
            mAdapter.submitData(lifecycle, res)
        }
    }

    private fun setCategory() {
        cAdapter = CategoryAdapter(listCategory)
        binding.rvCategory.apply {
            adapter = cAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun category() = lifecycleScope.launch {
        categoryViewModel.getCategory().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    setLoading()
                }
                is Resource.Error -> {
                    stopLoading()
                    setDialogError(res.message.toString())
                }
                is Resource.Success -> {
                    stopLoading()
                    setCategory()

                }
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