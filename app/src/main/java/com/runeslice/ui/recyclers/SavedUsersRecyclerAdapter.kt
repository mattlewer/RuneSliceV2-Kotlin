package com.runeslice.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.runeslice.databinding.LayoutSavedUserBinding
import com.runeslice.dataclass.User

class SavedUsersRecyclerAdapter(
    private val listener: OnItemClickListener,
    private val savedUsers: List<User>
) : RecyclerView.Adapter<SavedUsersRecyclerAdapter.CardViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, i: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = LayoutSavedUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(savedUsers[position])
    }

    override fun getItemCount() = savedUsers.size

    inner class CardViewHolder(private val binding: LayoutSavedUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                savedUsername.text = user.name

                searchSavedUserBtn.setOnClickListener { v ->
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, position)
                    }
                }
            }
        }
    }
}