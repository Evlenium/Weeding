package com.applications.weedingappcompetitions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Players(private val mapPlayers: MutableList<Player>) : Parcelable