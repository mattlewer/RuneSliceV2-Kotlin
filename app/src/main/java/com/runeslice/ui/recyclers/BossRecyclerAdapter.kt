package com.runeslice.ui.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.runeslice.MyApplication
import com.runeslice.databinding.CircularCardItemBinding
import com.runeslice.dataclass.Boss

class BossRecyclerAdapter(
    private val bosses: List<Boss>,
    private val navigateToSingleBoss: (boss: Boss, bossID: Int) -> Unit
) : RecyclerView.Adapter<BossRecyclerAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CircularCardItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(bosses[position], position)
    }

    override fun getItemCount() = bosses.size

    inner class CardViewHolder(private val binding: CircularCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(boss: Boss, position: Int) {
            binding.apply {
                include.circularImageSmallImg.setImageResource(MyApplication.bossImgs[position])

                cardTitle.text = boss.num.toString()
                cardSubtext.text = boss.name
                layoutCard.setPadding(10, 20, 10, 50)

                root.setOnClickListener {
                    navigateToSingleBoss(boss, position)
                }
            }
        }
    }
}