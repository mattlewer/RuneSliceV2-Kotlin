package com.runeslice.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.runeslice.CompareSavedUsersActivity
import com.runeslice.MyApplication
import com.runeslice.R
import com.runeslice.SearchedUserActivity
import com.runeslice.databinding.FragmentSavedUsersBinding
import com.runeslice.dataclass.User2
import com.runeslice.ui.recyclers.SavedUsersRecyclerAdapter
import com.runeslice.util.UserHelper
import java.lang.reflect.Type


class SavedUsersFragment : Fragment(),SavedUsersRecyclerAdapter.OnItemClickListener {

    private var _binding: FragmentSavedUsersBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null
    lateinit var userHelper : UserHelper

    private lateinit var savedUserList : MutableList<User2>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSavedUsersBinding.inflate(inflater, container, false)
        userHelper = UserHelper(requireContext().applicationContext)
        loadSavedUsers()
        prepareRecycler()
        binding.compareUsersBtn.setOnClickListener {
            if(MyApplication.savedUsers.size > 1){
                val intent = Intent(activity, CompareSavedUsersActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(context, "Save more than one user to compare!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loadSavedUsers()
        prepareRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

    fun loadSavedUsers(){
        try {
            val gson = Gson()
            val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val userJson: String? = sharedPrefs!!.getString("savedUsers", null)
            val listType: Type = object : TypeToken<MutableList<User2>>() {}.type
            savedUserList = gson.fromJson(userJson, listType)
        }catch (e: Exception){
            savedUserList = mutableListOf()
        }
    }

    fun prepareRecycler(){
        var recyclerView: RecyclerView = binding.savedUsersRecyclerView
        var adapter = SavedUsersRecyclerAdapter(this, savedUserList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(v: View, i: Int) {
        if(v.id == R.id.searchSavedUserBtn){
            userHelper.setSearchedUser(savedUserList[i])
            val intent = Intent(activity, SearchedUserActivity::class.java)
            startActivity(intent)
        }

    }

}