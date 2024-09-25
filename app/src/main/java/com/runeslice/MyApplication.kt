package com.runeslice

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.runeslice.dataclass.User2
import java.lang.Exception
import java.lang.reflect.Type

class MyApplication: Application()  {

    override fun onCreate() {
        super.onCreate()

        skillImgs = mutableListOf(
                R.drawable.skills_icon,
                R.drawable.attack_icon,
                R.drawable.defence_icon,
                R.drawable.strength_icon,
                R.drawable.hitpoints_icon,
                R.drawable.ranged_icon,
                R.drawable.prayer_icon,
                R.drawable.magic_icon,
                R.drawable.cooking_icon,
                R.drawable.woodcutting_icon,
                R.drawable.fletching_icon,
                R.drawable.fishing_icon,
                R.drawable.firemaking_icon,
                R.drawable.crafting_icon,
                R.drawable.smithing_icon,
                R.drawable.mining_icon,
                R.drawable.herblore_icon,
                R.drawable.agility_icon,
                R.drawable.thieving_icon,
                R.drawable.slayer_icon,
                R.drawable.farming_icon,
                R.drawable.runecraft_icon,
                R.drawable.hunter_icon,
                R.drawable.construction_icon
        )

        scrollImgs = mutableListOf(
            R.drawable.clue_scroll_beginner,
            R.drawable.clue_scroll_easy,
            R.drawable.clue_scroll_medium,
            R.drawable.clue_scroll_hard,
            R.drawable.clue_scroll_elite,
            R.drawable.clue_scroll_master
        )


        bossImgs = mutableListOf(
            R.drawable.abyssal_sire,
            R.drawable.alchemical_hydra,
            R.drawable.amoxliatl,
            R.drawable.araxxor,
            R.drawable.artio,
            R.drawable.barrows,
            R.drawable.bryophyta,
            R.drawable.callisto,
            R.drawable.calvarion,
            R.drawable.cerberus,
            R.drawable.chambers_of_xeric,
            R.drawable.chambers_of_xeric_challenge_mode,
            R.drawable.chaos_elemental,
            R.drawable.chaos_fanatic,
            R.drawable.commander_zilyana,
            R.drawable.corporeal_beast,
            R.drawable.crazy_archaeologist,
            R.drawable.dagannoth_prime,
            R.drawable.dagannoth_rex,
            R.drawable.dagannoth_supreme,
            R.drawable.deranged_archaeologist,
            R.drawable.duke_sucellus,
            R.drawable.general_graardor,
            R.drawable.giant_mole,
            R.drawable.grotesque_guardians,
            R.drawable.hespori,
            R.drawable.kalphite_queen,
            R.drawable.king_black_dragon,
            R.drawable.kraken,
            R.drawable.kreearra,
            R.drawable.kril_tsutsaroth,
            R.drawable.lunar_chest,
            R.drawable.the_mimic,
            R.drawable.nex,
            R.drawable.the_nightmare,
            R.drawable.the_nightmare_phosani,
            R.drawable.obor,
            R.drawable.phantom_muspah,
            R.drawable.sarachnis,
            R.drawable.scorpia,
            R.drawable.scurrius,
            R.drawable.skotizo,
            R.drawable.sol_heredit,
            R.drawable.spindel,
            R.drawable.tempoross,
            R.drawable.gauntlet,
            R.drawable.corrupted_gauntlet,
            R.drawable.the_hueycoatl,
            R.drawable.the_leviathan,
            R.drawable.the_whisperer,
            R.drawable.theatre_of_blood,
            R.drawable.theatre_of_blood_hard,
            R.drawable.thermonuclear_smoke_devil,
            R.drawable.tombs_of_amascut_normal,
            R.drawable.tombs_of_amascut_expert,
            R.drawable.tzkal_zuk,
            R.drawable.tztok_jad,
            R.drawable.vardorvis,
            R.drawable.venenatis,
            R.drawable.vetion,
            R.drawable.vorkath,
            R.drawable.wintertodt,
            R.drawable.zalcano,
            R.drawable.zulrah
        )



        xpLevels = mutableListOf(
                0,0,83,174,276,388,512,650,801,969,1154,1358,1584,1833
                , 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730
                , 10824, 12031, 13363, 14833, 16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224
                , 41171, 45529, 50229, 55649, 61512, 67983, 75127, 83014, 91721, 101333, 111945, 123660, 136594
                , 150872, 166636, 184040, 203254, 224466, 247886, 273742, 302288, 333804, 368599
                , 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895, 1096278, 1210421
                , 1336443, 1475581, 1629200, 1798068, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594
                , 3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558
                , 9684577, 10692629, 11805606, 13034431
        )

        savedUsers = setSaved()
    }



    companion object{
        lateinit var savedUsers: MutableList<User2>
        lateinit var skillImgs: MutableList<Int>
        lateinit var xpLevels: MutableList<Long>
        lateinit var scrollImgs: MutableList<Int>
        lateinit var bossImgs: MutableList<Int>
    }

    fun setSaved() : MutableList<User2>{
        var initialSaved = mutableListOf<User2>()
        try{
            val gson = Gson()
            val sharedPrefs = applicationContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val userJson : String? = sharedPrefs!!.getString("savedUsers", null)
            val listType: Type = object : TypeToken<MutableList<User2>>() {}.type
            initialSaved = gson.fromJson(userJson, listType)
        }catch(e:Exception){}
        return initialSaved
    }
}