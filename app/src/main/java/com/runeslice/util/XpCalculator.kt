package com.runeslice.util

import com.runeslice.MyApplication
import java.math.RoundingMode
import java.text.DecimalFormat

object XpCalculator {
    private const val MAX_SKILL_XP = 13034431.0
    private const val MAX_TOTAL_LEVEL = 2277.0
    private const val MAX_TOTAL_XP = 4600000000.0

    private val xpLevels = MyApplication.xpLevels

    fun getPercentThroughLevel(currentXp: Int): Double {
        if (currentXp >= MAX_SKILL_XP) return 100.0

        val nextLevelIdx = xpLevels.indexOfFirst { it > currentXp }
        if (nextLevelIdx == -1) return 100.0

        val currentLevelThreshold = xpLevels[nextLevelIdx - 1].toDouble()
        val nextLevelThreshold = xpLevels[nextLevelIdx].toDouble()

        return round((currentXp - currentLevelThreshold) / (nextLevelThreshold - currentLevelThreshold) * 100.0)
    }

    fun getPercentToNextTen(currentXp: Int): Double {
        if (currentXp >= MAX_SKILL_XP) return 100.0

        val nextTenIdx =
            xpLevels.indices.firstOrNull { it > 0 && it % 10 == 0 && xpLevels[it] > currentXp }
                ?: 99
        val startLevelIdx = if (nextTenIdx == 99) 90 else nextTenIdx - 10

        val startXp = xpLevels[startLevelIdx].toDouble()
        val targetXp = if (nextTenIdx == 99) MAX_SKILL_XP else xpLevels[nextTenIdx].toDouble()

        return round((currentXp - startXp) / (targetXp - startXp) * 100.0)
    }

    fun getPercentToNinetyNine(currentXp: Int): Double =
        round((currentXp / MAX_SKILL_XP) * 100.0)

    fun getOverallLevelPercent(totalLevel: Int): Double =
        round((totalLevel / MAX_TOTAL_LEVEL) * 100.0)

    fun getOverallXpPercent(totalXp: Long): Double =
        round((totalXp / MAX_TOTAL_XP) * 100.0)

    private fun round(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }
}