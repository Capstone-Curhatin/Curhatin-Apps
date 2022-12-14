package com.capstone.curhatin

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.utils.MySharedPreference
import com.capstone.curhatin.databinding.ActivityMainBinding
import com.capstone.curhatin.viewmodel.UserViewModel
import com.capstone.curhatin.viewmodel.WaitingRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val waitViewModel: WaitingRoomViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    @Inject lateinit var prefs: MySharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window?.setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        setContentView(binding.root)

        updateFcm()
        updateStatus(true)

        val bottomNav = binding.bottomNav
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHost.navController
        bottomNav.setupWithNavController(navController)

        if (prefs.getUser().role == 1) {
            bottomNav.menu[1].isEnabled = false
            bottomNav.menu[1].isVisible = false
        }

        navController.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.homeFragment,
                R.id.chatFragment,
                R.id.doctorChatFragment,
                R.id.profileFragment
                -> binding.bottomNav.visibility = View.VISIBLE
                else -> binding.bottomNav.visibility = View.GONE
            }
        }
    }

    private fun updateStatus(status: Boolean){
        val request = WaitingRoomRequest(prefs.getUser().id, online = status)
        waitViewModel.updateWaitingRoom(request).observe(this){ res ->
            if (res is Resource.Success) Timber.d("OKE")
        }
    }

    private fun updateFcm(){
        userViewModel.fetch().observe(this){ res ->
            if (res is Resource.Success){
                val fcm = res.data?.data?.fcm
                Timber.d("New Token: $fcm --> ${prefs.getFcm()}")
            }
        }
    }

    override fun onResume() {
        updateStatus(true)
        super.onResume()
    }

    override fun onPause() {
        updateStatus(false)
        super.onPause()
    }

    override fun onDestroy() {
        updateStatus(false)
        super.onDestroy()
    }

}