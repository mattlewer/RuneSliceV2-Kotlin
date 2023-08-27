package com.runeslice.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.runeslice.MyApplication
import com.runeslice.R
import com.runeslice.dataclass.User2


class SavedUserRankingRecycler(private var type: String, private var name: String, private var savedUsers: MutableList<User2>): RecyclerView.Adapter<SavedUserRankingRecycler.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_ranked_user,
            parent, false
        )
        return CardViewHolder(itemView)
    }

    // Bind data to items when the position currently displayed in the Recycler View
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {


        when(position){
            0 -> holder.rankImage.setImageResource(R.drawable.crown_gold)
            1 -> holder.rankImage.setImageResource(R.drawable.crown_silver)
            2 -> holder.rankImage.setImageResource(R.drawable.crown_bronze)
            else -> holder.rankImage.setImageResource(R.drawable.gnome_child)
        }

        holder.username.text = savedUsers[position].name


        when(type){
            "Skill" ->{
                var selectedSkill = savedUsers[position].skills.find { skill -> skill.name == name }
                holder.xp.text = "%,d".format(selectedSkill!!.xp)
                holder.level.text = selectedSkill!!.level.toString()
            }
            "Boss"->{
                holder.xpH.text = "Kills"
                var selectedBoss = savedUsers[position].boss.find { boss -> boss.name == name }
                holder.xp.text = selectedBoss!!.num.toString()
                holder.level.visibility = View.GONE
                holder.levelH.visibility = View.GONE
            }
            "Clue" ->{
                holder.xpH.text = "Completed"
                var selectedClue = savedUsers[position].clues.find { clue -> clue.name == name }
                holder.xp.text = selectedClue!!.num.toString()
                holder.level.visibility = View.GONE
                holder.levelH.visibility = View.GONE
            }
        }
    }

    // Always returns items.size
    override fun getItemCount() = savedUsers.size

    // Always need to build custom builder class - take data - set to viewholder
    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var rankImage = itemView.findViewById<ImageView>(R.id.rankImage)
        var username = itemView.findViewById<TextView>(R.id.usernameText)
        var xpH = itemView.findViewById<TextView>(R.id.xpHeader)
        var xp = itemView.findViewById<TextView>(R.id.xpValue)
        var level = itemView.findViewById<TextView>(R.id.levelValue)
        var levelH = itemView.findViewById<TextView>(R.id.levelHeader)
    }



}