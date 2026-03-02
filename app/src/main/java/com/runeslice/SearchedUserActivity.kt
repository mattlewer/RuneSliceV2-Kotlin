package com.runeslice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import com.runeslice.databinding.ActivitySearchUserBinding
import com.runeslice.dataclass.User
import com.runeslice.util.UserHelper

class SearchedUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchUserBinding
    private lateinit var currentUser: User
    private val userManager by lazy { UserHelper(this) }
    private var isSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        loadSearchedUser()
        setupNavigation()
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        updateSaveStatus()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            windowInsets
        }
    }

    private fun loadSearchedUser() {
        val sharedPrefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val userJson = sharedPrefs.getString("user", null) ?: return

        currentUser = Gson().fromJson(userJson, User::class.java)
        binding.usernameHeader.text = currentUser.name
        updateSaveStatus()
    }

    private fun updateSaveStatus() {
        isSaved = MyApplication.savedUsers.any { it.name == currentUser.name }

        val icon =
            if (isSaved) R.drawable.ic_baseline_saved_24 else R.drawable.ic_outline_not_saved_24
        binding.saveUserBtn.setImageResource(icon)
    }

    private fun setupClickListeners() {
        binding.saveUserBtn.setOnClickListener {
            if (isSaved) {
                userManager.removeUser(currentUser)
            } else {
                userManager.saveUser(currentUser)
            }
            updateSaveStatus()
        }

        binding.returnToSearchBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}