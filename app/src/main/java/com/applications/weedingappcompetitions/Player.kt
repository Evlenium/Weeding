package com.applications.weedingappcompetitions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(val id: Int, var name: String, var vinNum: Int) :
    Parcelable
