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
import com.capstone.core.ui.adapter.StoryPagingAdapter
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentHomeBinding
import com.capstone.curhatin.viewmodel.CategoryViewModel
import com.capstone.curhatin.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryViewModel by viewModels()
    private lateinit var mAdapter: StoryPagingAdapter
    private lateinit var cAdapter: CategoryAdapter
    private val categoryViewModel: CategoryViewModel by viewModels()

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

        setRecycler()
//        setCategory()
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

//        cAdapter = CategoryAdapter()
//        binding.rvCategory.apply {
//            adapter = cAdapter
//            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
//        }

        mAdapter.setOnClickListener {
            navigateDirection(
                HomeFragmentDirections.actionHomeFragmentToCommentFragment(it)
            )
        }
    }

//    private fun setCategory() {
//        categoryViewModel.getCategory().observe(viewLifecycleOwner) { res ->
//            val list = ArrayList<Category>()
//            list.add(Category(0, "All"))
//            res.data?.data?.forEach {
//                list.add(it)
//            }
//
//            cAdapter.setData = list
//        }
//
//        cAdapter.setOnItemClick {
//            setDataRecycler(it.id)
//            Timber.d("ID: ${it.id}")
//        }
//    }

//    private fun setDataRecycler(id: Int){
//        if (id == 0){
//            viewModel.getStories().observe(viewLifecycleOwner) { res ->
//                mAdapter.submitData(lifecycle, res)
//            }
//        }else{
//            viewModel.getStoryByCategory(id).observe(viewLifecycleOwner){ res ->
//                Timber.d("Data Category: $res")
//                mAdapter.submitData(lifecycle, res)
//            }
//        }
//    }

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