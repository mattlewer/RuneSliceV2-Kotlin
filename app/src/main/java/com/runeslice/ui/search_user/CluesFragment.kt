package com.runeslice.ui.search_user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.runeslice.R
import com.runeslice.databinding.FragmentCluesBinding
import com.runeslice.dataclass.User

class CluesFragment : Fragment() {

    private var _binding: FragmentCluesBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentUser: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCluesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCurrentUser()
        setupClueImages()
        updateClueValues()
    }

    private fun loadCurrentUser() {
        val sharedPrefs = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val userJson = sharedPrefs.getString("user", null) ?: return
        currentUser = Gson().fromJson(userJson, User::class.java)
    }

    private fun setupClueImages() {
        binding.apply {
            beginnerClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_beginner)
            easyClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_easy)
            mediumClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_medium)
            hardClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_hard)
            eliteClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_elite)
            masterClueImg.circularImageMediumImg.setImageResource(R.drawable.clue_scroll_master)
        }
    }

    private fun updateClueValues() {
        val clues = currentUser.clues
        if (clues.size < 6) return

        binding.apply {
            beginnerClueValue.text = clues[0].num.toString()
            easyClueValue.text = clues[1].num.toString()
            mediumClueValue.text = clues[2].num.toString()
            hardClueValue.text = clues[3].num.toString()
            eliteClueValue.text = clues[4].num.toString()
            masterClueValue.text = clues[5].num.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}