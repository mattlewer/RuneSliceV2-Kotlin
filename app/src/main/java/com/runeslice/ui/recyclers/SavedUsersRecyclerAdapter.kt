package com.runeslice.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.runeslice.R
import com.runeslice.dataclass.User2

class SavedUsersRecyclerAdapter(private val listener: OnItemClickListener, private var savedUsers: MutableList<User2>): RecyclerView.Adapter<SavedUsersRecyclerAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.layout_saved_user,
                parent, false
        )
        return CardViewHolder(itemView)
    }

    // Bind data to items when the position currently displayed in the Recycler View
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.savedUsername.text = savedUsers[position].name
    }

    // Always returns items.size
    override fun getItemCount() = savedUsers.size

    // Always need to build custom builder class - take data - set to viewholder
    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var savedUsername = itemView.findViewById<TextView>(R.id.savedUsername)
        var searchSavedUserBtn = itemView.findViewById<TextView>(R.id.searchSavedUserBtn)

        init{
            searchSavedUserBtn.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(v, position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(v: View, i:Int)
    }

}