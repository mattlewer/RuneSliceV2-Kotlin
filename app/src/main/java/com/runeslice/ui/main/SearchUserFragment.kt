package com.runeslice.ui.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.runeslice.SearchedUserActivity
import com.runeslice.databinding.FragmentSearchUserBinding
import com.runeslice.util.UserHelper

class SearchUserFragment : Fragment() {

    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var userHelper: UserHelper
    private lateinit var dia: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)

        userHelper = UserHelper(requireContext())
        dia = com.runeslice.ui.helpers.ProgressDialog.progressDialog(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchUserBtn.setOnClickListener {
            val username = binding.editUsername.text.toString().trim()

            if (username.isNotEmpty()) {
                dia.show()

                userHelper.getData(username, isManualSearch = true) { success ->
                    dia.dismiss()
                    if (success) {
                        navigateToSearchedUser()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "No user entered", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToSearchedUser() {
        val intent = Intent(requireContext(), SearchedUserActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (dia.isShowing) dia.dismiss()
        _binding = null
    }
}