package com.capstone.core.utils

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.capstone.core.data.common.DialogType
import com.capstone.core.ui.dialog.PopupDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import timber.log.Timber

fun EditText.getTextTrim() =
    this.text.toString().trim()

fun Fragment.quickShowToast(msg: String) =
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

fun Fragment.navigateDirection(directions: NavDirections) =
    findNavController().navigate(directions)

fun Fragment.navigateBack() =
    findNavController().navigateUp()

fun Fragment.setDialogSuccess(type: DialogType, msg: String) =
    PopupDialog(DialogType.SUCCESS, msg, object : PopupDialog.DialogCallback{
        override fun dismissDialog(dialog: DialogFragment) {
            Timber.d("OKE")
            dialog.dismiss()
        }
    }).show(parentFragmentManager, null)
