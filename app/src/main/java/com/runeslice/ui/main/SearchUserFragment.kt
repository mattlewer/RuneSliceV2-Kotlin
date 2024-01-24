package com.runeslice.ui.main

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.runeslice.BASE_URL
import com.runeslice.SearchedUserActivity
import com.runeslice.databinding.FragmentSearchUserBinding
import com.runeslice.dataclass.User2
import com.runeslice.util.ApiInterface
import com.runeslice.util.UserBuilder
import com.runeslice.util.UserHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException


class SearchUserFragment : Fragment() {

    lateinit private var userHelper: UserHelper

    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null

    lateinit private var userBuilder : UserBuilder
    lateinit private var searchUserBtn: Button
    lateinit private var editUsername: EditText

    lateinit var dia : Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        searchUserBtn = binding.searchUserBtn
        editUsername = binding.editUsername
        userBuilder = UserBuilder(requireContext())
        userHelper = UserHelper(requireContext())

        dia = com.runeslice.ui.helpers.ProgressDialog.progressDialog(requireContext())

        binding.searchUserBtn.setOnClickListener {
            if(editUsername.text.toString() != ""){
                dia.show()
                getData(editUsername.text.toString())
            }else{
                Toast.makeText(context, "No user entered", Toast.LENGTH_LONG).show()
            }
        }
        binding.searchMeBtn.setOnClickListener { searchMe() }
        binding.searchMeBtn.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

    private fun getData(username: String){
        if(userHelper.isOnline(requireContext()) == true) {
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
                        userHelper.setSearchedUser(userData)

                        val intent = Intent(activity, SearchedUserActivity::class.java)
                        startActivity(intent)
                        dia.dismiss()
                    } catch (e: IndexOutOfBoundsException) {
                        dia.dismiss()
                        Toast.makeText(context, "A new boss or skill has been added, ensure you have updated RuneSlice", Toast.LENGTH_LONG).show()
                    }catch(e: Exception){
                        dia.dismiss()
                        Toast.makeText(context, "Error: User not found", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(context, "Error: Please try again", Toast.LENGTH_LONG).show()
                }
            })
        }else{
            Toast.makeText(context, "Network Error: No network detected", Toast.LENGTH_SHORT).show()
        }

    }


    private fun searchMe(){
        if(checkIfSaved() == true){
            val intent = Intent(activity, SearchedUserActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(context, "No username saved", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkIfSaved() : Boolean{
        return if(loadCurrentUser() == null) false else true
    }

    fun loadCurrentUser() : User2?{
        var currentUser : User2? = null
        try{
            val gson = Gson()
            val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val userJson : String? = sharedPrefs!!.getString("user", "")
            currentUser = gson.fromJson(userJson, User2::class.java)
        }catch (e: Exception){
            // Do nothing
        }
        return currentUser
    }


}