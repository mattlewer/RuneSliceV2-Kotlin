package com.runeslice.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.runeslice.BASE_URL
import com.runeslice.MyApplication
import com.runeslice.dataclass.User2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type
import java.lang.ArrayIndexOutOfBoundsException

class UserHelper(var context: Context) {

    fun setSearchedUser(userData: User2) {
        val sharedPrefs = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val edit = sharedPrefs!!.edit()
        val gson = Gson()
        val dataJson = gson.toJson(userData)
        edit.apply {
            putString("user", dataJson)
        }.apply()
    }

    fun saveUser(userData: User2) {
        MyApplication.savedUsers.add(userData)
        val sharedPrefs = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val edit = sharedPrefs!!.edit()
        val gson = Gson()
        val mutableDataJson = gson.toJson(MyApplication.savedUsers)
        edit.apply {
            putString("savedUsers", mutableDataJson)
        }.apply()
    }

    fun removeUser(userData: User2) {
        var usersToRemove = mutableListOf<User2>()
        MyApplication.savedUsers.map {
            if (it.name == userData.name) {
                usersToRemove.add(it)
            }
        }
        MyApplication.savedUsers.removeAll(usersToRemove)
        val sharedPrefs = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val edit = sharedPrefs!!.edit()
        val gson = Gson()
        val mutableDataJson = gson.toJson(MyApplication.savedUsers)
        edit.apply {
            putString("savedUsers", mutableDataJson)
        }.apply()
    }

    fun updateUser(userData: User2) {
        MyApplication.savedUsers.map {
            if (it.name == userData.name) {
                it.skills = userData.skills
                it.boss = userData.boss
                it.clues = userData.clues
            }
        }
        val sharedPrefs = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val edit = sharedPrefs!!.edit()
        val gson = Gson()
        val mutableDataJson = gson.toJson(MyApplication.savedUsers)
        edit.apply {
            putString("savedUsers", mutableDataJson)
        }.apply()
    }

    fun setSavedUsers() {
        val gson = Gson()
        val sharedPrefs = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val userJson: String? = sharedPrefs!!.getString("savedUsers", null)
        val listType: Type = object : TypeToken<MutableList<User2>>() {}.type
        MyApplication.savedUsers = gson.fromJson(userJson, listType)
    }

    fun updateAllSavedUsers() {
        MyApplication.savedUsers.forEach { user ->
            getData(user.name)
        }
    }

    fun getData(username: String) {
        if (isOnline(context) == true) {
            var userBuilder = UserBuilder(context)
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
            val retrofitData = retrofitBuilder.getUser(username)

            retrofitData.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    try {
                        val responseBody = response.body()!!
                        val userData: User2 = userBuilder.prepareUser(username, responseBody)
                        updateUser(userData)
                    } catch (e: NullPointerException) {
                        Toast.makeText(
                            context,
                            "Error: Check username and try again",
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        Toast.makeText(
                            context,
                            "A new boss or skill has been added to the game, a new app version will be available shortly...",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(context, "Error: Please try again", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                }
            }
        }
        return false
    }

}