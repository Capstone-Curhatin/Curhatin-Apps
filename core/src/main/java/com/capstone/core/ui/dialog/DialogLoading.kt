package com.capstone.core.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import com.capstone.core.R
import com.capstone.core.databinding.DialogCustomBinding
import com.capstone.core.databinding.DialogLoadingBinding

object DialogLoading {

    private var dialog: Dialog? = null

    fun start(context: Context) {
        dialog = setDialog(context)
    }

    fun stop(){
        if (dialog?.isShowing == true) dialog?.cancel()
    }

    private fun setDialog(context: Context): Dialog? {
        dialog = Dialog(context)
        dialog.let {
            it?.show()
            it?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it?.setContentView(R.layout.dialog_loading)
            it?.setCancelable(false)
            it?.setCanceledOnTouchOutside(false)
            return it
        }
    }


}