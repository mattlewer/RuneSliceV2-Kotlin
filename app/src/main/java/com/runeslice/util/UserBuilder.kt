package com.runeslice.util

import android.content.Context
import com.runeslice.R
import com.runeslice.dataclass.Boss
import com.runeslice.dataclass.ClueScroll
import com.runeslice.dataclass.Skill
import com.runeslice.dataclass.User2

class UserBuilder(val context: Context) {

    fun prepareUser(username: String, response: String) : User2{
        val textstr : MutableList<String> = response.lines().toMutableList()
        var userStatElements: MutableList<MutableList<String>> = arrayListOf()
        for(line in 0..textstr.size -1){
            var elements = textstr[line].split(",").toMutableList()
            userStatElements.add(elements)
        }
        return groupUpStats(username, userStatElements)
    }

    fun groupUpStats(username: String, elements: MutableList<MutableList<String>>) : User2 {
        println(elements)
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

        for(x in 0..24){
            println(skillNames[x])
            println(elements[x])
            userSkillElements.add(Skill(skillNames[x], elements[x][0].toInt(), elements[x][1].toInt(),elements[x][2].toInt()))
        }
        for(x in 33..38){
            println(scrollNames[x-33])
            println(elements[x])
            userScrollElements.add(ClueScroll(scrollNames[x-33], elements[x][0].toInt(),elements[x][1].toInt()))
        }
        for(x in 45..elements.size-2){
            println(bossNames[x-45])
            println(elements[x])
            userBossElements.add(Boss(bossNames[x-45], elements[x][0].toInt(),elements[x][1].toInt()))
        }
        return User2(username, userSkillElements, userBossElements, userScrollElements)
    }
}