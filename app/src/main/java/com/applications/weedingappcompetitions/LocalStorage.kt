package com.applications.weedingappcompetitions

import android.content.Context
import com.applications.weedingappcompetitions.Constants.PLAYERS
import com.google.gson.Gson

class LocalStorage(val context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(PLAYERS, Context.MODE_PRIVATE)
    private val gson = Gson()
    fun savePlayerList(playerList: List<Player>) {
        sharedPreferences.edit().putString(PLAYERS, gson.toJson(playerList)).apply()
    }

    fun clearSharedPreference() {
        sharedPreferences.edit().clear().apply()
    }

    fun getPlayerList(): List<Player> {
        val stringShared = sharedPreferences.getString(PLAYERS, null)
        return if (!stringShared.isNullOrEmpty()) {
            createPlayerListFromJson(stringShared, gson)
        } else emptyList()
    }

    fun updateList(input: Int, player: Player) {
        updatePlayerList(playerList = getPlayerList(), input = input, player = player)
    }

    private fun updatePlayerList(
        playerList: List<Player>,
        player: Player, input: Int
    ) {
        for (person in playerList) {
            if (person.id == player.id) {
                person.lastNum = input
                break
            }
        }
        sharedPreferences.edit().putString(PLAYERS, gson.toJson(playerList)).apply()
    }

    private fun createPlayerListFromJson(json: String, gson: Gson): List<Player> {
        return gson.fromJson(json, Array<Player>::class.java).toList()
    }
}