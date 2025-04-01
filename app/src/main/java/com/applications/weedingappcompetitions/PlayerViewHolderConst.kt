package com.applications.weedingappcompetitions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.applications.weedingappcompetitions.databinding.ItemPlayerConstBinding

class PlayerViewHolderConst(
    itemView: View,
    private val players: List<Player>,
) :
    RecyclerView.ViewHolder(itemView) {
    private val binding = ItemPlayerConstBinding.bind(itemView)
    fun bindPosition(position: Int) {
        val pos = position + 1
        binding.numPlayer.text = pos.toString()
        binding.textViewName.text = players[position].name
    }
}