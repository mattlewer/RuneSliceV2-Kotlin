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
import com.runeslice.databinding.FragmentCompareBossesBinding
import com.runeslice.databinding.FragmentCompareClueBinding
import com.runeslice.dataclass.User2
import com.runeslice.ui.recyclers.SavedUserRankingRecycler

class CompareBossesFragment : Fragment() {

    private var _binding: FragmentCompareBossesBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null
    lateinit var spinner: Spinner
    lateinit var userRanking : MutableList<User2>

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SavedUserRankingRecycler

    var selectedBoss = 0
    lateinit var bossArray : Array<String>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompareBossesBinding.inflate(inflater, container, false)
        bossArray = resources.getStringArray(R.array.bosses)
        userRanking = MyApplication.savedUsers
        spinner = binding.spinnerBoss
        prepareRecycler()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {} // We don't need this but we have to provide
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                userRanking.sortByDescending { it.boss[id.toInt()].num}
                selectedBoss = id.toInt()
                recyclerView.adapter = SavedUserRankingRecycler("Boss", bossArray[selectedBoss], userRanking)
                binding.include.circularImageMediumImg.setImageResource(MyApplication.bossImgs[selectedBoss])
                recyclerView.scheduleLayoutAnimation()
            }
        }
        binding.include.circularImageMediumImg.setImageResource(MyApplication.bossImgs[selectedBoss])
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

    fun prepareRecycler(){
        recyclerView = binding.compareBossRecyclerView
        adapter = SavedUserRankingRecycler("Boss", bossArray[selectedBoss], userRanking)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.setHasFixedSize(true)
    }
}