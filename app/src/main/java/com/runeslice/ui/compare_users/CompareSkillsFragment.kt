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
import com.runeslice.databinding.FragmentCompareSkillsBinding
import com.runeslice.dataclass.User
import com.runeslice.ui.recyclers.SavedUserRankingRecycler

class CompareSkillsFragment : Fragment() {

    private var _binding: FragmentCompareSkillsBinding? = null
    private val binding get() = _binding!!

    private val userRanking: MutableList<User> = MyApplication.savedUsers
    private lateinit var skillsArray: Array<String>
    private var selectedSkillIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompareSkillsBinding.inflate(inflater, container, false)
        skillsArray = resources.getStringArray(R.array.skills)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()
    }

    private fun setupRecyclerView() {
        binding.compareSkillRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            updateRanking()
        }
    }

    private fun setupSpinner() {
        binding.spinnerSkill.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSkillIndex = position
                updateRanking()
            }
        }
    }

    private fun updateRanking() {
        userRanking.sortByDescending { it.skills[selectedSkillIndex].xp }

        binding.apply {
            include.circularImageMediumImg.setImageResource(MyApplication.skillImgs[selectedSkillIndex])

            compareSkillRecyclerView.adapter = SavedUserRankingRecycler(
                "Skill",
                skillsArray[selectedSkillIndex],
                userRanking
            )

            compareSkillRecyclerView.scheduleLayoutAnimation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}