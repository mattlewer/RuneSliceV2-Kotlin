package com.runeslice.ui.search_user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.runeslice.R
import com.runeslice.databinding.FragmentBossesBinding
import com.runeslice.dataclass.Boss
import com.runeslice.dataclass.User
import com.runeslice.ui.recyclers.BossRecyclerAdapter

class BossesFragment : Fragment() {

    private var _binding: FragmentBossesBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentUser: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBossesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCurrentUser()
        setupRecyclerView()
    }

    private fun loadCurrentUser() {
        val sharedPrefs = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val userJson = sharedPrefs.getString("user", null) ?: return

        currentUser = Gson().fromJson(userJson, User::class.java)
    }

    private fun setupRecyclerView() {
        val navigateToSingleBoss: (Boss, Int) -> Unit = { boss, bossID ->
            val bundle = Bundle().apply {
                putParcelable("boss", boss)
                putInt("bossID", bossID)
            }
            findNavController().navigate(R.id.singleBossFragment, bundle)
        }

        binding.bossRecyclerView.apply {
            adapter = BossRecyclerAdapter(currentUser.boss, navigateToSingleBoss)
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}