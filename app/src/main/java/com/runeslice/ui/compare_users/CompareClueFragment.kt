package com.runeslice.ui.compare_users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.runeslice.MyApplication
import com.runeslice.R
import com.runeslice.databinding.FragmentCompareClueBinding
import com.runeslice.dataclass.User
import com.runeslice.ui.recyclers.SavedUserRankingRecycler

class CompareClueFragment : Fragment() {

    private var _binding: FragmentCompareClueBinding? = null
    private val binding get() = _binding!!

    private val userRanking: MutableList<User> = MyApplication.savedUsers
    private lateinit var clueNames: Array<String>
    private var selectedClueIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompareClueBinding.inflate(inflater, container, false)
        clueNames = resources.getStringArray(R.array.clues)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()
    }

    private fun setupRecyclerView() {
        binding.compareClueRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            updateRanking()
        }
    }

    private fun setupSpinner() {
        binding.spinnerClue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedClueIndex = position
                updateRanking()
            }
        }
    }

    private fun updateRanking() {
        userRanking.sortByDescending { it.clues[selectedClueIndex].num }

        binding.apply {
            include.circularImageMediumImg.setImageResource(MyApplication.scrollImgs[selectedClueIndex])

            compareClueRecyclerView.adapter = SavedUserRankingRecycler(
                "Clue",
                clueNames[selectedClueIndex],
                userRanking
            )

            compareClueRecyclerView.scheduleLayoutAnimation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}