package com.capstone.curhatin.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.update.UpdateRequest
import com.capstone.core.utils.*
import com.capstone.curhatin.R
import com.capstone.curhatin.auth.forgot.NewPasswordFragmentDirections
import com.capstone.curhatin.databinding.FragmentEditProfileBinding
import com.capstone.curhatin.databinding.FragmentLoginBinding
import com.capstone.curhatin.viewmodel.StoryViewModel
import com.capstone.curhatin.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private var picture: File? = null
    private val viewModel: UserViewModel by viewModels()

    @Inject
    lateinit var pref: MySharedPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener { navigateBack() }
        binding.evProfile.setOnClickListener { updateImage() }
        binding.tick.setOnClickListener { sendObservable() }

        defaultValue()
    }

    private fun updateImage() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())
            this.picture = myFile
            binding.ivUser.setImageURI(selectedImg)
            binding.ivUser.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun sendObservable() {
        with(binding) {
            val name = etUsername.getTextTrim()
            val phone = etPhone.getTextTrim()
            val picture = picture
            val request = UpdateRequest( name = name, phone = phone, picture = picture)

            viewModel.updateUser(request).observe(viewLifecycleOwner){ res ->
                when(res){
                    is Resource.Loading -> {setLoading()}
                    is Resource.Error -> {
                        stopLoading()
                        setDialogError(res.message.toString())
                    }
                    is Resource.Success -> {
                        stopLoading()
                        pref.setUser(res.data?.data!!)
                        setDialogSuccess(resources.getString(R.string.update_profile_message_alert))
                        navigateDirection(
                            EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
                        )
                    }
                }
            }
        }
    }

    private fun defaultValue(){
        with(binding) {
            etUsername.setText(pref.getUser().name)
            etPhone.setText(pref.getUser().phone)
            ivUser.setImageUrl(pref.getUser().picture.toString())
        }
    }

}