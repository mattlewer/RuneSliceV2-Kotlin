package com.runeslice.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.runeslice.MyApplication
import com.runeslice.R
import com.runeslice.dataclass.Skill

class SkillRecyclerAdapter(
    private val skills: MutableList<Skill>,
    private val navigateToSingleBoss: (skill: Skill, skillID: Int) -> Unit
) : RecyclerView.Adapter<SkillRecyclerAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.circular_card_item,
            parent, false
        )
        return CardViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.image.setImageResource(MyApplication.skillImgs[position])
        holder.kills.text = skills[position].level.toString()
        holder.name.text = ""
        holder.card.setPadding(10, 20, 10, 50)
        holder.card.setOnClickListener {
            navigateToSingleBoss(skills[position], position)
        }
    }

    override fun getItemCount() = skills.size

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card = itemView.findViewById<ConstraintLayout>(R.id.layoutCard)
        var image: ImageView = itemView.findViewById(R.id.circular_image_small_img)
        val kills: TextView = itemView.findViewById(R.id.cardTitle)
        val name: TextView = itemView.findViewById(R.id.cardSubtext)
    }
}