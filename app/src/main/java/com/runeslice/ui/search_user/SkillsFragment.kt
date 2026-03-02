package com.runeslice.ui.search_user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.runeslice.R
import com.runeslice.databinding.FragmentSkillsBinding
import com.runeslice.dataclass.Skill
import com.runeslice.dataclass.User
import com.runeslice.ui.recyclers.SkillRecyclerAdapter

class SkillsFragment : Fragment(){

    private var _binding: FragmentSkillsBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null
    lateinit var currentUser: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSkillsBinding.inflate(inflater, container, false)
        loadCurrentUser()
        prepareRecycler()
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

    fun loadCurrentUser(){
        val gson = Gson()
        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val userJson : String? = sharedPrefs!!.getString("user", "")
        currentUser = gson.fromJson(userJson, User::class.java)
    }

    fun prepareRecycler(){
        var recyclerView: RecyclerView = binding.skillRecyclerView
        var navigateToSingleSkill: (skill: Skill, skillID: Int) -> Unit = { skill, skillID ->
            var bundle = Bundle()
            navController!!.navigate(
                R.id.singleSkillFragment,
                bundle.apply {
                    putParcelable("skill", skill)
                    putInt("position", skillID)
                },
                null,
            )
        }
        var adapter = SkillRecyclerAdapter(currentUser.skills, navigateToSingleSkill)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(binding.root.context, 4)
        recyclerView.setHasFixedSize(true)
    }
}