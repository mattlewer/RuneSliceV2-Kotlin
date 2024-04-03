package com.runeslice.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Boss(
    val name: String,
    val rank: Int,
    val num: Int,
): Parcelable
