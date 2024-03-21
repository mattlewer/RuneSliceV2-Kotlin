package com.runeslice.util

import android.content.Context
import com.runeslice.R
import com.runeslice.dataclass.Boss
import com.runeslice.dataclass.ClueScroll
import com.runeslice.dataclass.Skill
import com.runeslice.dataclass.User2

class UserBuilder(val context: Context) {

    fun prepareUser(username: String, response: String) : User2{
        // Split into lines
        val textstr : MutableList<String> = response.lines().toMutableList()
        // For each line of stats, split into fields based on ','
        var userStatElements: MutableList<MutableList<String>> = arrayListOf()
        for(line in 0..textstr.size -1){
            var elements = textstr[line].split(",").toMutableList()
            userStatElements.add(elements)
        }

        // Group the stats into Skills, Bosses and Clue Scrolls
        return groupUpStats(username, userStatElements)
    }

    //23 = last stat
    //28 - 33 = Clues
    //36 - end = Bosses
    fun groupUpStats(username: String, elements: MutableList<MutableList<String>>) : User2 {

        // Replace -1 with 0
        for(x in 0..elements.size-2){
            for( y in 0..elements[x].size-1){
                if( elements[x][y] == "-1"){
                    elements[x][y] = "0"
                }
            }
        }

        var userSkillElements: MutableList<Skill> = arrayListOf()
        var userScrollElements: MutableList<ClueScroll> = arrayListOf()
        var userBossElements: MutableList<Boss> = arrayListOf()

        val skillNames = context.resources.getStringArray(R.array.skills)
        val scrollNames = context.resources.getStringArray(R.array.clues)
        val bossNames = context.resources.getStringArray(R.array.bosses)

        for(x in 0..23){ userSkillElements.add(Skill(skillNames[x], elements[x][0].toInt(), elements[x][1].toInt(),elements[x][2].toInt())) }
        for(x in 31..36){ userScrollElements.add(ClueScroll(scrollNames[x-31], elements[x][0].toInt(),elements[x][1].toInt())) }
        for(x in 42..elements.size-2){ userBossElements.add(Boss(bossNames[x-42], elements[x][0].toInt(),elements[x][1].toInt())) }
        return User2(username, userSkillElements, userBossElements, userScrollElements)
    }
}