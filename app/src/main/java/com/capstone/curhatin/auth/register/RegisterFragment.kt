package com.capstone.curhatin.auth.register

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.LinearLayout
import com.capstone.curhatin.R
import com.google.android.material.bottomsheet.BottomSheetDialog

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.core.utils.navigateBack
import com.capstone.core.utils.navigateDirection
import com.capstone.curhatin.auth.AuthViewModel
import com.capstone.curhatin.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loginUser.setOnClickListener { navigateBack() }
            registerDoctor.setOnClickListener { navigateDirection(
                RegisterFragmentDirections.actionRegisterFragmentToRegisterDoctorFragment()
            )}
        }
    }

}


////custom dialog
//val btn = findViewById<Button>(R.id.custom_dialog)
//
//btn.setOnClickListener{
//    val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog_register,null)
//
//    val myDialog = Dialog(this)
//    myDialog.setContentView(dialogBinding)
//
//    myDialog.setCancelable(true)
//    myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    myDialog.show()
//
//    val yesbtn = dialogBinding.findViewById<Button>(R.id.alert_button)
//    yesbtn.setOnClickListener{
//        myDialog.dismiss()
//    }
//}
//
////otp
//val button: Button = findViewById(R.id.custom_dialog2)
//
//button.setOnClickListener {
//    val bottomSheetDialog = BottomSheetDialog(
//        this@MainActivity, R.style.BottomSheetDialogTheme
//    )
//
//    val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
//        R.layout.layout_bottom_sheet,
//        findViewById<LinearLayout>(R.id.bottom_sheet)
//    )
//
//    bottomSheetDialog.setContentView(bottomSheetView)
//    bottomSheetDialog.setCancelable(false)
//    bottomSheetDialog.show()
//}
