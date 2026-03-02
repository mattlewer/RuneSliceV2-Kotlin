package com.runeslice.ui.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.runeslice.MyApplication
import com.runeslice.databinding.CircularCardItemBinding
import com.runeslice.dataclass.Skill

class SkillRecyclerAdapter(
    private val skills: List<Skill>,
    private val navigateToSingleSkill: (skill: Skill, skillID: Int) -> Unit
) : RecyclerView.Adapter<SkillRecyclerAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CircularCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(skills[position], position)
    }

    override fun getItemCount() = skills.size

    inner class CardViewHolder(private val binding: CircularCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(skill: Skill, position: Int) {
            binding.apply {
                include.circularImageSmallImg.setImageResource(MyApplication.skillImgs[position])
                cardTitle.text = skill.level.toString()
                cardSubtext.text = ""
                layoutCard.setPadding(10, 20, 10, 20)

                root.setOnClickListener {
                    navigateToSingleSkill(skill, position)
                }
            }
        }
    }
}