package com.capstone.curhatin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.utils.*
import com.capstone.curhatin.databinding.FragmentCreateStoryBinding
import com.capstone.curhatin.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CreateStoryFragment : Fragment() {

    private var _binding: FragmentCreateStoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryViewModel by viewModels()

    @Inject lateinit var prefs: MySharedPreference
    private var categoryId: Int? = null
    private var isAnonymous = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvUserName.text = prefs.getUser().name
        binding.userPicture.setImageUrl(prefs.getUser().picture.toString())
        Timber.e("PICTURE: ${prefs.getUser().picture}")

        binding.btbnUpload.setOnClickListener { sendRequest() }
        binding.imgBack.setOnClickListener { navigateBack() }

        isAnonymous = prefs.getAnonymous()
        binding.isAnonymous.isChecked = isAnonymous
        // get value from switch button
        binding.isAnonymous.setOnCheckedChangeListener { _, _ ->
            isAnonymous = !isAnonymous
        }

        // show dialog category
        binding.tvCategory.setOnClickListener {
            DialogCategory(object : DialogCategory.DialogCategoryCallback {
                override fun onClickCategory(dialog: DialogFragment, id: Int, categoryName: String) {
                    categoryId = id
                    binding.tvCategory.text = categoryName
                    dialog.dismiss()
                }
            }).show(parentFragmentManager, null)
        }

    }

    // send request to API
    private fun sendRequest() {
        val body = binding.edtBody.getTextTrim()
        val request = StoryRequest(body, categoryId, isAnonymous)
        viewModel.createStory(request).observe(viewLifecycleOwner){ res ->
            when(res){
                is Resource.Loading -> {setLoading()}
                is Resource.Error -> {
                    stopLoading()
                    setDialogError(res.message.toString())
                }
                is Resource.Success -> {
                    setDialogSuccess(Constant.SUCCESS_CREATE_STORY)
                    stopLoading()
                    navigateBack()
                }
            }
        }
    }
}