package com.runeslice.ui.compare_users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.runeslice.MyApplication
import com.runeslice.R
import com.runeslice.databinding.FragmentCompareClueBinding
import com.runeslice.databinding.FragmentCompareSkillsBinding
import com.runeslice.dataclass.User2
import com.runeslice.ui.recyclers.SavedUserRankingRecycler


class CompareClueFragment : Fragment() {

    private var _binding: FragmentCompareClueBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null
    lateinit var spinner: Spinner
    lateinit var userRanking : MutableList<User2>

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SavedUserRankingRecycler

    var selectedClue = 0
    lateinit var clueArray : Array<String>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompareClueBinding.inflate(inflater, container, false)
        clueArray = resources.getStringArray(R.array.clues)
        userRanking = MyApplication.savedUsers
        spinner = binding.spinnerClue
        prepareRecycler()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {} // We don't need this but we have to provide
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                userRanking.sortByDescending { it.clues[id.toInt()].num}
                selectedClue = id.toInt()
                recyclerView.adapter = SavedUserRankingRecycler("Clue", clueArray[selectedClue], userRanking)
                binding.include.circularImageMediumImg.setImageResource(MyApplication.scrollImgs[selectedClue])
                recyclerView.scheduleLayoutAnimation()
            }
        }
        binding.include.circularImageMediumImg.setImageResource(MyApplication.skillImgs[selectedClue])
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

    fun prepareRecycler(){
        recyclerView = binding.compareClueRecyclerView
        adapter = SavedUserRankingRecycler("Clue", clueArray[selectedClue], userRanking)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.setHasFixedSize(true)
    }

}