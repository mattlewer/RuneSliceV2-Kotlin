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
import com.runeslice.databinding.FragmentCompareBossesBinding
import com.runeslice.dataclass.User
import com.runeslice.ui.recyclers.SavedUserRankingRecycler

class CompareBossesFragment : Fragment() {

    private var _binding: FragmentCompareBossesBinding? = null
    private val binding get() = _binding!!

    private val userRanking: MutableList<User> = MyApplication.savedUsers
    private lateinit var bossNames: Array<String>
    private var selectedBossIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompareBossesBinding.inflate(inflater, container, false)
        bossNames = resources.getStringArray(R.array.bosses)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()
    }

    private fun setupRecyclerView() {
        binding.compareBossRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            updateRanking()
        }
    }

    private fun setupSpinner() {
        binding.spinnerBoss.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedBossIndex = position
                updateRanking()
            }
        }
    }

    private fun updateRanking() {
        userRanking.sortByDescending { it.boss[selectedBossIndex].num }

        binding.apply {
            include.circularImageMediumImg.setImageResource(MyApplication.bossImgs[selectedBossIndex])

            compareBossRecyclerView.adapter = SavedUserRankingRecycler(
                "Boss",
                bossNames[selectedBossIndex],
                userRanking
            )

            compareBossRecyclerView.scheduleLayoutAnimation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}