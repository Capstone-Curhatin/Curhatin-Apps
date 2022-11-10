package com.capstone.core.utils

import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

fun EditText.getTextTrim() =
    this.text.toString().trim()

fun Fragment.quickShowToast(msg: String) =
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()