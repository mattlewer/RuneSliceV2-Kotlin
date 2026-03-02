package com.runeslice.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.runeslice.CompareSavedUsersActivity
import com.runeslice.MyApplication
import com.runeslice.R
import com.runeslice.SearchedUserActivity
import com.runeslice.databinding.FragmentSavedUsersBinding
import com.runeslice.ui.recyclers.SavedUsersRecyclerAdapter
import com.runeslice.util.UserHelper

class SavedUsersFragment : Fragment(), SavedUsersRecyclerAdapter.OnItemClickListener {

    private var _binding: FragmentSavedUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var userHelper: UserHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedUsersBinding.inflate(inflater, container, false)
        userHelper = UserHelper(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        userHelper.setSavedUsers()
        updateUI()
    }

    private fun setupButtons() {
        binding.compareUsersBtn.setOnClickListener {
            if (MyApplication.savedUsers.size > 1) {
                startActivity(Intent(requireContext(), CompareSavedUsersActivity::class.java))
            } else {
                Toast.makeText(context, "Save at least two users to compare!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.savedUsersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        updateUI()
    }

    private fun updateUI() {
        binding.savedUsersRecyclerView.adapter =
            SavedUsersRecyclerAdapter(this, MyApplication.savedUsers)
    }

    override fun onItemClick(v: View, i: Int) {
        if (v.id == R.id.searchSavedUserBtn) {
            val selectedUser = MyApplication.savedUsers[i]
            userHelper.setSearchedUser(selectedUser)

            val intent = Intent(requireContext(), SearchedUserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}