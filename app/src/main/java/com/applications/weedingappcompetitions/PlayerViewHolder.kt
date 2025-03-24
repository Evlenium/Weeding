package com.applications.weedingappcompetitions

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.applications.weedingappcompetitions.databinding.ItemPlayerBinding

class PlayerViewHolder(
    itemView: View,
    private val players: List<Player>,
    private val saveNameListener: SaveNameListener
) :
    RecyclerView.ViewHolder(itemView) {
    private val binding = ItemPlayerBinding.bind(itemView)
    fun bindPosition(position: Int) {
        val pos = position + 1
        binding.numPlayer.text = pos.toString()
        binding.editTextName.addTextChangedListener(
            beforeTextChanged = { s, start, count, after -> },
            onTextChanged = { s, start, before, count -> },
            afterTextChanged = { s ->
                if (!s.isNullOrEmpty()) {
                    players[position].name = s.toString()
                    saveNameListener.onSave(players)
                }
            })
    }
}