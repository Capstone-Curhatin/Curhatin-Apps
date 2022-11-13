package com.capstone.core.utils

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp

fun EditText.getTextTrim() =
    this.text.toString().trim()

fun Fragment.quickShowToast(msg: String) =
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

fun Fragment.navigateDirection(directions: NavDirections) =
    findNavController().navigate(directions)

fun Fragment.navigateBack() =
    findNavController().navigateUp()