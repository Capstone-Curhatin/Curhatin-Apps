package com.capstone.core.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import com.capstone.core.R

object DialogFinding {

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
            it?.setContentView(R.layout.dialog_finding)
            it?.setCancelable(false)
            it?.setCanceledOnTouchOutside(false)
            return it
        }
    }

}