package com.capstone.core.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.capstone.core.R
import com.capstone.core.data.common.DialogType
import com.capstone.core.databinding.DialogCustomBinding

class PopupDialog(
    private val type: DialogType,
    private val message: String,
    private val callback: DialogCallback
    ) : DialogFragment() {

    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialogCallback: DialogCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_rectangle_white)
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        dialog?.window?.setLayout(width, height)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(type){
            DialogType.SUCCESS -> { binding.lottieSuccess.visibility = View.VISIBLE }
            DialogType.ERROR -> { binding.lottieError.visibility = View.VISIBLE }
            DialogType.SAD -> {binding.lottieSad.visibility = View.VISIBLE}
        }

        binding.tvMessage.text = message

        binding.btnDismiss.setOnClickListener {
            dialogCallback.dismissDialog(this)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dialogCallback = callback
        }catch (e: Exception){
            throw ClassCastException(context.toString() + "must be implement DialogCallback")
        }
    }

    interface DialogCallback{
        fun dismissDialog(dialog: DialogFragment)
    }

}