package com.applications.weedingappcompetitions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class PlayerAdapter(
    private val players: List<Player>,
    private val saveNameListener: SaveNameListener
) :
    ListAdapter<Player, PlayerViewHolder>(Comparator()) {

    class Comparator : DiffUtil.ItemCallback<Player>() {
        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindPosition(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view, players, saveNameListener)
    }
}