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
import com.capstone.core.ui.adapter.CategoryAdapter
import com.capstone.core.ui.adapter.StoryPagingAdapter
import com.capstone.core.utils.*

import com.capstone.core.utils.MySharedPreference
import com.capstone.core.utils.navigateDirection
import com.capstone.curhatin.databinding.FragmentHomeBinding
import com.capstone.curhatin.viewmodel.AuthViewModel
import com.capstone.curhatin.viewmodel.CategoryViewModel
import com.capstone.curhatin.viewmodel.StoryViewModel
import com.capstone.curhatin.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var mAdapter: StoryPagingAdapter
    private lateinit var cAdapter: CategoryAdapter
    private val categoryVM: CategoryViewModel by viewModels()
    private var categoryId: Int? = null

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
        setCategory()
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

        viewModel.getStories().observe(viewLifecycleOwner) { res ->
            mAdapter.submitData(lifecycle, res)
        }
    }

    private fun setCategory() {
        cAdapter = CategoryAdapter()
        binding.rvCategory.apply {
            adapter = cAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }
        categoryVM.getCategory().observe(viewLifecycleOwner) { res ->
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
                    cAdapter = CategoryAdapter()
                    binding.rvCategory.apply {
                        adapter = cAdapter
                        layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                        itemAnimator = DefaultItemAnimator()
                    }
                    cAdapter.setData = res.data?.data!!

                }
            }

        }
        cAdapter.setOnItemClick {
            categoryId = it.id
            setRecycler()
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