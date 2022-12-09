package com.capstone.core.utils

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.capstone.core.R
import com.capstone.core.data.common.DialogType
import com.capstone.core.ui.dialog.DialogFinding
import com.capstone.core.ui.dialog.DialogLoading
import com.capstone.core.ui.dialog.PopupDialog
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.setImageUrl(url: String) =
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_person)
        .into(this)

fun ImageView.setImageDrawable(drawable: Int) =
    Glide.with(this)
        .load(drawable)
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
this function call dialog with sad animation
 */
fun Fragment.setDialogSad(msg: String) =
    PopupDialog(DialogType.SAD, msg, object : PopupDialog.DialogCallback{
        override fun dismissDialog(dialog: DialogFragment) {
            dialog.dismiss()
        }
    }).show(parentFragmentManager, null)

/**
    this function for call loading dialog
 */
fun Fragment.setLoading() =
    DialogLoading.start(requireContext())

fun stopLoading() = DialogLoading.stop()

/**
    this function for call finding dialog
 */
fun Fragment.setFinding() =
    DialogFinding.start(requireContext())

fun stopFinding() = DialogFinding.stop()

fun Fragment.toTermsAndConditions(){
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://sites.google.com/view/curhatin-terms-conditions")
    startActivity(intent)
}

fun Fragment.toPrivacyPolicy(){
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://sites.google.com/view/curhatin-privacy-policy")
    startActivity(intent)
}

val timeStamp: String = SimpleDateFormat(
    "dd-MMM-yyyy",
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}