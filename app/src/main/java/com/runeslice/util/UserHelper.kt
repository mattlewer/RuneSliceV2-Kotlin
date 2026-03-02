package com.runeslice.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.runeslice.MyApplication
import com.runeslice.dataclass.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserHelper(private val context: Context) {

    private val gson = Gson()
    private val sharedPrefs = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

    companion object {
        private var hasShownUpdateError = false
        private var lastErrorTime = 0L
        private const val ERROR_COOLDOWN = 10000L
    }

    private fun commitSavedUsers() {
        val json = gson.toJson(MyApplication.savedUsers)
        sharedPrefs.edit { putString("savedUsers", json) }
    }

    fun setSearchedUser(userData: User) {
        val dataJson = gson.toJson(userData)
        sharedPrefs.edit { putString("user", dataJson) }
    }

    fun saveUser(userData: User) {
        if (MyApplication.savedUsers.none { it.name == userData.name }) {
            MyApplication.savedUsers.add(userData)
            commitSavedUsers()
        }
    }

    fun removeUser(userData: User) {
        val removed = MyApplication.savedUsers.removeAll { it.name == userData.name }
        if (removed) commitSavedUsers()
    }

    fun updateUser(userData: User) {
        val existingUser = MyApplication.savedUsers.find { it.name == userData.name }
        existingUser?.let {
            it.skills = userData.skills
            it.boss = userData.boss
            it.clues = userData.clues
            commitSavedUsers()
        }
    }

    fun setSavedUsers() {
        val userJson = sharedPrefs.getString("savedUsers", null) ?: return
        val listType = object : TypeToken<MutableList<User>>() {}.type
        val list: MutableList<User>? = gson.fromJson(userJson, listType)
        if (list != null) {
            MyApplication.savedUsers = list
        }
    }

    fun updateAllSavedUsers() {
        hasShownUpdateError = false
        MyApplication.savedUsers.forEach { user ->
            getData(user.name, isManualSearch = false)
        }
    }

    fun getData(
        username: String,
        isManualSearch: Boolean = false,
        onResult: ((Boolean) -> Unit)? = null
    ) {
        if (!isOnline(context)) {
            onResult?.invoke(false)
            return
        }

        val userBuilder = UserBuilder(context)

        RetrofitClient.apiInterface.getUser(username).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                try {
                    val responseBody = response.body() ?: throw Exception("Empty body")
                    val userData = userBuilder.prepareUser(username, responseBody)

                    updateUser(userData)

                    if (isManualSearch) {
                        setSearchedUser(userData)
                    }

                    onResult?.invoke(true)
                } catch (e: Exception) {
                    handleError(e)
                    onResult?.invoke(false)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                handleError(Exception("network"))
                onResult?.invoke(false)
            }
        })
    }

    private fun handleError(e: Exception) {
        val currentTime = System.currentTimeMillis()

        when (e) {
            is ArrayIndexOutOfBoundsException -> {
                if (!hasShownUpdateError) {
                    Toast.makeText(context, "Game update detected! New version coming soon...", Toast.LENGTH_LONG).show()
                    hasShownUpdateError = true
                }
            }
            else -> {
                if (currentTime - lastErrorTime > ERROR_COOLDOWN) {
                    Toast.makeText(context, "Error updating profiles. Check your connection.", Toast.LENGTH_SHORT).show()
                    lastErrorTime = currentTime
                }
            }
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                )
    }
}