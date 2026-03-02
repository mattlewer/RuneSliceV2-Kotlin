package com.runeslice.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.runeslice.R
import com.runeslice.databinding.LayoutRankedUserBinding
import com.runeslice.dataclass.User

class SavedUserRankingRecycler(
    private val type: String,
    private val name: String,
    private val savedUsers: List<User>
) : RecyclerView.Adapter<SavedUserRankingRecycler.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = LayoutRankedUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(savedUsers[position], position)
    }

    override fun getItemCount() = savedUsers.size

    inner class CardViewHolder(private val binding: LayoutRankedUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, position: Int) {
            binding.apply {
                rankImage.setImageResource(
                    when (position) {
                        0 -> R.drawable.crown_gold
                        1 -> R.drawable.crown_silver
                        2 -> R.drawable.crown_bronze
                        else -> R.drawable.gnome_child
                    }
                )

                usernameText.text = user.name

                levelValue.visibility = View.VISIBLE
                levelHeader.visibility = View.VISIBLE

                when (type) {
                    "Skill" -> {
                        xpHeader.text = "XP"
                        val skill = user.skills.find { it.name == name }
                        xpValue.text = "%,d".format(skill?.xp ?: 0)
                        levelValue.text = skill?.level?.toString() ?: "1"
                    }

                    "Boss" -> {
                        xpHeader.text = "Kills"
                        val boss = user.boss.find { it.name == name }
                        xpValue.text = boss?.num?.toString() ?: "0"
                        hideLevel()
                    }

                    "Clue" -> {
                        xpHeader.text = "Completed"
                        val clue = user.clues.find { it.name == name }
                        xpValue.text = clue?.num?.toString() ?: "0"
                        hideLevel()
                    }
                }
            }
        }

        private fun hideLevel() {
            binding.levelValue.visibility = View.GONE
            binding.levelHeader.visibility = View.GONE
        }
    }
}