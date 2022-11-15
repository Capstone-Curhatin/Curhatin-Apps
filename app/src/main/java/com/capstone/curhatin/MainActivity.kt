package com.capstone.curhatin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.curhatin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}