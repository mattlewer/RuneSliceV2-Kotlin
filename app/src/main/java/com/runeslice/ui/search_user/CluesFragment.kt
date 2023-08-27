package com.runeslice.ui.search_user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.runeslice.R
import com.runeslice.databinding.FragmentCluesBinding
import com.runeslice.dataclass.User2


class CluesFragment : Fragment() {

    private var _binding: FragmentCluesBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null

    lateinit var currentUser: User2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCluesBinding.inflate(inflater, container, false)
        loadCurrentUser()


        binding.beginnerClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_beginner)
        binding.easyClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_easy)
        binding.mediumClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_medium)
        binding.hardClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_hard)
        binding.eliteClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_elite)
        binding.masterClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_master)

        binding.beginnerClueValue.text = currentUser.clues[0].num.toString()
        binding.easyClueValue.text = currentUser.clues[1].num.toString()
        binding.mediumClueValue.text = currentUser.clues[2].num.toString()
        binding.hardClueValue.text = currentUser.clues[3].num.toString()
        binding.eliteClueValue.text = currentUser.clues[4].num.toString()
        binding.masterClueValue.text = currentUser.clues[5].num.toString()

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



}