package com.runeslice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.runeslice.databinding.ActivityCompareSavedUsersBinding
import com.runeslice.databinding.ActivitySearchUserBinding

class CompareSavedUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompareSavedUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompareSavedUsersBinding.inflate(layoutInflater)
        prepareNav()
        binding.returnToSearchBtn.setOnClickListener { onBackPressed() }
        setContentView(binding.root)
    }


    fun prepareNav(){
        var navView = binding.bottomNavigationView2
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_compare) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
}