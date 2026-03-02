package com.runeslice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.runeslice.databinding.ActivityCompareSavedUsersBinding
import com.runeslice.databinding.ActivitySearchUserBinding

class CompareSavedUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompareSavedUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        binding = ActivityCompareSavedUsersBinding.inflate(layoutInflater)
        prepareNav()
        binding.returnToSearchBtn.setOnClickListener { onBackPressed() }
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            windowInsets
        }
    }


    fun prepareNav(){
        var navView = binding.bottomNavigationView2
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_compare) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
}