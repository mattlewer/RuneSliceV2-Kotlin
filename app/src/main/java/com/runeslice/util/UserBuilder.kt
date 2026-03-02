package com.runeslice.util

import android.content.Context
import com.runeslice.R
import com.runeslice.dataclass.Boss
import com.runeslice.dataclass.ClueScroll
import com.runeslice.dataclass.Skill
import com.runeslice.dataclass.User

class UserBuilder(private val context: Context) {

    fun prepareUser(username: String, response: String): User {
        // Clean up the response and convert to a list of lists of strings
        val userStatElements = response.lines()
            .filter { it.isNotBlank() }
            .map { line ->
                line.split(",").map { if (it == "-1") "0" else it }
            }

        return groupUpStats(username, userStatElements)
    }

    private fun groupUpStats(username: String, elements: List<List<String>>): User {
        val userSkillElements = mutableListOf<Skill>()
        val userScrollElements = mutableListOf<ClueScroll>()
        val userBossElements = mutableListOf<Boss>()

        val skillNames = context.resources.getStringArray(R.array.skills)
        val scrollNames = context.resources.getStringArray(R.array.clues)
        val bossNames = context.resources.getStringArray(R.array.bosses)

        // 1. Parse Skills (Indices 0 to 24)
        skillNames.forEachIndexed { index, name ->
            if (index < elements.size) {
                val row = elements[index]
                userSkillElements.add(Skill(name, row[0].toInt(), row[1].toInt(), row[2].toInt()))
            }
        }

        // 2. Parse Clue Scrolls (Starting at index 33)
        scrollNames.forEachIndexed { index, name ->
            val apiIndex = index + 33
            if (apiIndex < elements.size) {
                val row = elements[apiIndex]
                userScrollElements.add(ClueScroll(name, row[0].toInt(), row[1].toInt()))
            }
        }

        // 3. Parse Bosses (Starting at index 45)
        bossNames.forEachIndexed { index, name ->
            val apiIndex = index + 45
            if (apiIndex < elements.size) {
                val row = elements[apiIndex]
                userBossElements.add(Boss(name, row[0].toInt(), row[1].toInt()))
            }
        }

        return User(username, userSkillElements, userBossElements, userScrollElements)
    }
}