package com.runeslice.util

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    // Query adds '?value=username' onto end of GET URL
    @GET("index_lite.ws")
    fun getUser(@Query("player") username:String): Call<String>

}