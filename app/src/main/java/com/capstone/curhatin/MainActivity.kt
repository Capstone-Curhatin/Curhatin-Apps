package com.capstone.curhatin

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        setContentView(binding.root)

        updateFcm()
        updateStatus(true)

        val bottomNav = binding.bottomNav
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHost.navController
        bottomNav.setupWithNavController(navController)

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
        val request = WaitingRoomRequest(prefs.getUser().id, online = status, "")
        waitViewModel.updateWaitingRoom(request)
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