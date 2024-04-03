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
import com.runeslice.dataclass.Boss

class BossRecyclerAdapter(
    private val bosses: MutableList<Boss>,
    private val navigateToSingleBoss: (boss: Boss, bossID: Int) -> Unit
) : RecyclerView.Adapter<BossRecyclerAdapter.CardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.circular_card_item,
            parent, false
        )
        return CardViewHolder(itemView)
    }

    // Bind data to items when the position currently displayed in the Recycler View
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.image.setImageResource(MyApplication.bossImgs[position])
        holder.name.text = bosses[position].name
        holder.kills.text = bosses[position].num.toString()
        holder.card.setPadding(10, 20, 10, 50)
        holder.card.setOnClickListener {
            navigateToSingleBoss(bosses[position], position)
        }
    }

    // Always returns items.size
    override fun getItemCount() = bosses.size

    // Always need to build custom builder class - take data - set to viewholder
    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card = itemView.findViewById<ConstraintLayout>(R.id.layoutCard)
        var image: ImageView = itemView.findViewById(R.id.circular_image_small_img)
        val kills: TextView = itemView.findViewById(R.id.cardTitle)
        val name: TextView = itemView.findViewById(R.id.cardSubtext)
    }

}