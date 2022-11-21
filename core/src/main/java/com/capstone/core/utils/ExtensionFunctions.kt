package com.capstone.core.utils

import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.capstone.core.data.common.DialogType
import com.capstone.core.ui.dialog.DialogLoading
import com.capstone.core.ui.dialog.PopupDialog
import timber.log.Timber

fun ImageView.setImageUrl(url: String) =
    Glide.with(this)
        .load(url)
        .into(this)

// get edittext text toString
fun EditText.getTextTrim() =
    this.text.toString().trim()

// show toast
fun Fragment.quickShowToast(msg: String) =
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

/**
    the function for navigate to another fragment
    this function same with call navigation (findNavController.navigate(dest))
 */
fun Fragment.navigateDirection(directions: NavDirections) =
    findNavController().navigate(directions)

/**
    the function for navigate back to previous fragment
    this function same with call navigation (findNavController.navigateUp())
 */
fun Fragment.navigateBack() =
    findNavController().navigateUp()


/**
    this function call dialog with success animation
 */
fun Fragment.setDialogSuccess(msg: String) =
    PopupDialog(DialogType.SUCCESS, msg, object : PopupDialog.DialogCallback{
        override fun dismissDialog(dialog: DialogFragment) {
            dialog.dismiss()
        }
    }).show(parentFragmentManager, null)

/**
    this function call dialog with error animation
 */
fun Fragment.setDialogError(msg: String) =
    PopupDialog(DialogType.ERROR, msg, object : PopupDialog.DialogCallback{
        override fun dismissDialog(dialog: DialogFragment) {
            dialog.dismiss()
        }
    }).show(parentFragmentManager, null)

/**
    this function for call loading dialog
 */
fun Fragment.setLoading() =
    DialogLoading.start(requireContext())

fun Fragment.stopLoading() = DialogLoading.stop()