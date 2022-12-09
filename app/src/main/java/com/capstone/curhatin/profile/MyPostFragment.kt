package com.capstone.curhatin.profile

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
import com.capstone.core.ui.adapter.StoryPagingAdapter
import com.capstone.core.utils.MySharedPreference
import com.capstone.core.utils.navigateBack
import com.capstone.core.utils.navigateDirection
import com.capstone.core.utils.setImageUrl
import com.capstone.curhatin.databinding.FragmentMyPostBinding
import com.capstone.curhatin.home.HomeFragmentDirections
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
        binding.circleImageView.setImageUrl(pref.getUser().picture.toString())


        binding.backArrow.setOnClickListener { navigateBack() }

        setRecycler()
        loadState()
        checkRole()
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
        mAdapter.setOnClickListener {
            navigateDirection(
                MyPostFragmentDirections.actionMyPostFragmentToCommentFragment(it)
            )
        }
    }

    private fun checkRole(){
        if (pref.getUser().role == 1){
            binding.roleBasic.visibility = View.GONE
            binding.rolePremium.visibility = View.GONE
            binding.roleDoctor.visibility = View.VISIBLE
        }else checkPremium()
    }

    private fun checkPremium() {
        if (pref.getUser().is_premium) {
            binding.roleBasic.visibility = View.GONE
            binding.rolePremium.visibility = View.VISIBLE
            binding.roleDoctor.visibility = View.GONE
        } else {
            binding.roleBasic.visibility = View.VISIBLE
            binding.rolePremium.visibility = View.GONE
            binding.roleDoctor.visibility = View.GONE
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