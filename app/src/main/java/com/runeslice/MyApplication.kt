package com.runeslice

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.runeslice.constants.AppConstants
import com.runeslice.dataclass.User
import java.lang.Exception
import java.lang.reflect.Type

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        savedUsers = loadSavedUsers()
    }

    private fun loadSavedUsers(): MutableList<User> {
        return try {
            val gson = Gson()
            val sharedPrefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
            val userJson: String? = sharedPrefs.getString("savedUsers", null)

            if (userJson == null) return mutableListOf()

            val listType: Type = object : TypeToken<MutableList<User>>() {}.type
            gson.fromJson(userJson, listType) ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    companion object {
        lateinit var savedUsers: MutableList<User>

        val skillImgs: List<Int> get() = AppConstants.SKILL_IMGS
        val scrollImgs: List<Int> get() = AppConstants.SCROLL_IMGS
        val bossImgs: List<Int> get() = AppConstants.BOSS_IMGS
        val xpLevels: List<Long> get() = AppConstants.XP_LEVELS
    }
}