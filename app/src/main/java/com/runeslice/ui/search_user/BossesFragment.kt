package com.runeslice.ui.search_user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.runeslice.R
import com.runeslice.databinding.FragmentBossesBinding
import com.runeslice.dataclass.Boss
import com.runeslice.dataclass.User2
import com.runeslice.ui.recyclers.BossRecyclerAdapter

class BossesFragment : Fragment(){

    private var _binding: FragmentBossesBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null
    lateinit var currentUser: User2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBossesBinding.inflate(inflater, container, false)
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
        currentUser = gson.fromJson(userJson, User2::class.java)
    }

    fun prepareRecycler(){
        var recyclerView: RecyclerView = binding.bossRecyclerView
        var navigateToSingleBoss: (boss: Boss, bossID: Int) -> Unit = { boss, bossID ->
            var bundle = Bundle()
            navController!!.navigate(
                R.id.singleBossFragment,
                bundle.apply {
                    putParcelable("boss", boss)
                    putInt("bossID", bossID)
                },
                null,
            )
        }
        var adapter = BossRecyclerAdapter(currentUser.boss, navigateToSingleBoss)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(binding.root.context, 3)
        recyclerView.setHasFixedSize(true)
    }
}