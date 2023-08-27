package com.runeslice

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.runeslice.databinding.ActivityMainBinding
import com.runeslice.util.UserHelper

const val BASE_URL = "https://secure.runescape.com/m=hiscore_oldschool/"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        try{ setSavedUsers() }
        catch (e: Exception){}// No Saved Users
        var navView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        try{ setSavedUsers() }
        catch (e: Exception){}// No Saved Users
    }

    fun setSavedUsers(){
        var userManager = UserHelper(this)
        userManager.setSavedUsers()
        userManager.updateAllSavedUsers()
    }

}