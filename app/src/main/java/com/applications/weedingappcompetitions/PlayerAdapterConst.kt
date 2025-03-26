package com.applications.weedingappcompetitions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class PlayerAdapterConst(
    private val players: List<Player>,
    private val playerClickListener: PlayerClickListener,
) :
    ListAdapter<Player, PlayerViewHolderConst>(Comparator()) {

    class Comparator : DiffUtil.ItemCallback<Player>() {
        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PlayerViewHolderConst, position: Int) {
        holder.bindPosition(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolderConst {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_player_const, parent, false)
        return PlayerViewHolderConst(
            view,
            playerClickListener = playerClickListener,
            players = players
        )
    }
}