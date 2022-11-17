package com.capstone.curhatin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.core.ui.adapter.StoryPagingAdapter
import com.capstone.curhatin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mAdapter: StoryPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycler()
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

}