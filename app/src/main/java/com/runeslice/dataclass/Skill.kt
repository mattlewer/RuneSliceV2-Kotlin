package com.runeslice.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Skill(
    val name: String,
    val rank: Int,
    val level: Int,
    val xp: Int
) : Parcelable
