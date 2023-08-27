package com.runeslice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import com.runeslice.databinding.ActivitySearchUserBinding
import com.runeslice.dataclass.User2
import com.runeslice.util.UserHelper

class SearchedUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchUserBinding
    lateinit var currentUser : User2
    lateinit var userManager : UserHelper
    var isSaved : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUserBinding.inflate(layoutInflater)

        loadSearchedUser()
        prepareNav()

        binding.usernameHeader.text = currentUser.name
        binding.saveUserBtn.setOnClickListener { saveOrDeleteUser() }
        binding.returnToSearchBtn.setOnClickListener { onBackPressed() }
        userManager = UserHelper(applicationContext)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        isSaved = false
        loadSearchedUser()
        prepareNav()
    }

    fun loadSearchedUser(){
        val gson = Gson()
        val sharedPrefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val userJson : String? = sharedPrefs!!.getString("user", "")
        currentUser = gson.fromJson(userJson, User2::class.java)
        binding.saveUserBtn.setImageResource(R.drawable.ic_outline_not_saved_24)
        MyApplication.savedUsers.map{if (it.name == currentUser.name){
            isSaved = true
            binding.saveUserBtn.setImageResource(R.drawable.ic_baseline_saved_24)
        }}
    }

    fun saveOrDeleteUser(){
        if(isSaved){
            isSaved = false
            userManager.removeUser(currentUser)
            binding.saveUserBtn.setImageResource(R.drawable.ic_outline_not_saved_24)
        }else{
            userManager.saveUser(currentUser)
            binding.saveUserBtn.setImageResource(R.drawable.ic_baseline_saved_24)
        }
    }

    fun prepareNav(){
        var navView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }

}