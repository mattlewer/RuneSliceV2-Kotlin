package com.runeslice.ui.search_user

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.runeslice.MyApplication
import com.runeslice.R
import com.runeslice.databinding.FragmentSingleSkillBinding
import com.runeslice.dataclass.Skill
import java.math.RoundingMode
import java.text.DecimalFormat

class SingleSkillFragment : Fragment() {

    private var _binding: FragmentSingleSkillBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null

    private val XP_LEVELS = MyApplication.xpLevels

    private lateinit var skillIcon: ImageView
    private lateinit var skillText: TextView

    private lateinit var skillLevelText : TextView
    private lateinit var skillXPText : TextView
    private lateinit var skillRankText : TextView
    private lateinit var toNextLevelText: TextView
    private lateinit var toNextLevelBar: ProgressBar
    private lateinit var nextLevelPercentText: TextView
    private lateinit var toNextMultipleOfTenText: TextView
    private lateinit var toNextMultipleOfTenBar: ProgressBar
    private lateinit var nextTenLevelPercentText : TextView
    private lateinit var toNinetyNineBar: ProgressBar
    private lateinit var ninetyNinePercentText : TextView

    lateinit var skill: Skill

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleSkillBinding.inflate(inflater, container, false)

        skill = requireArguments().getParcelable("skill")!!

        skillIcon = binding.singleSkillIcon
        skillText = binding.skillText

        skillLevelText = binding.skillLevelText
        skillXPText = binding.skillXPText
        skillRankText = binding.skillRankText

        toNextLevelText = binding.toNextLevelText
        toNextLevelBar = binding.toNextLevelBar
        nextLevelPercentText = binding.nextLevelPercentText

        toNextMultipleOfTenText = binding.toNextMultipleOfTenText
        toNextMultipleOfTenBar = binding.toNextMultipleOfTenBar
        nextTenLevelPercentText = binding.nextTenLevelPercentText
        toNinetyNineBar = binding.toNinetyNineBar
        ninetyNinePercentText = binding.ninetyNinePercentText

        prepareLayout()

        sharedElementEnterTransition = TransitionInflater.from(this.context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(this.context).inflateTransition(android.R.transition.move)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

    private fun prepareLayout(){
        var position = requireArguments().getInt("position")
        skillIcon.setImageResource(MyApplication.skillImgs[position])
        skillIcon.transitionName = skill.name.toLowerCase() +"Img"
        skillText.text = skill.name
        skillLevelText.text = skill.level.toString()
        skillXPText.text = "%,d".format(skill.xp)
        skillRankText.text = "%,d".format(skill.rank)

        if(skill.name != "Overall"){
            calculatePercentThroughLevel()
            calculatePercentToNextTen()
            calculatePercentToNinetyNine()
        }else{
            prepareOverall()
        }
    }

    private fun prepareOverall(){
        binding.toNinetyNineSection.visibility = View.INVISIBLE

        toNextLevelText.text = "To Max Level:"
        var percentThrough :Double = roundOffDecimal((skill.level.toDouble() / 2277F) *100F)
        nextLevelPercentText.text = percentThrough.toString() + "%"
        toNextLevelBar.progress = percentThrough.toInt()


        toNextMultipleOfTenText.text = "To Max XP:"
        var percentThroughXP : Double = roundOffDecimal((skill.xp.toDouble() / 4600000000F) * 100F)
        nextTenLevelPercentText.text = percentThroughXP.toString() + "%"
        toNextMultipleOfTenBar.progress = percentThroughXP.toInt()

    }

    private fun calculatePercentThroughLevel(){
        for(i in 0..XP_LEVELS.size-1){
            if(skill.xp > 13034431){
                var percentThrough = 100
                nextLevelPercentText.text = percentThrough.toString() + "%"
                toNextLevelBar.progress = percentThrough
                break
            }
            else if(XP_LEVELS[i] > skill.xp){
                var percentThrough : Double = roundOffDecimal((skill.xp.toDouble() - XP_LEVELS[i-1].toDouble()) / (XP_LEVELS[i].toDouble() - XP_LEVELS[i-1].toDouble()) * 100F)
                nextLevelPercentText.text = percentThrough.toString() + "%"
                toNextLevelBar.progress = percentThrough.toInt()
                break
            }
        }
    }

    private fun calculatePercentToNextTen(){
        for(i in 0..XP_LEVELS.size-1){
            if(skill.xp > 13034431){
                var percentThrough = 100
                nextTenLevelPercentText.text = percentThrough.toString() + "%"
                toNextMultipleOfTenBar.progress = percentThrough
                break
            }
            else if(XP_LEVELS[i] > skill.xp && i % 10 == 0){
                var percentThrough = roundOffDecimal((skill.xp.toDouble() - XP_LEVELS[i-10].toDouble()) / (XP_LEVELS[i].toDouble() - XP_LEVELS[i-10].toDouble()) * 100F)
                nextTenLevelPercentText.text = percentThrough.toString() + "%"
                toNextMultipleOfTenBar.progress = percentThrough.toInt()
                break
            }else if(i == 99){
                var percentThrough = roundOffDecimal((skill.xp.toDouble() - XP_LEVELS[i-10].toDouble()) / (13034431 - XP_LEVELS[i-10].toDouble()) * 100F)
                nextTenLevelPercentText.text = percentThrough.toString() + "%"
                toNextMultipleOfTenBar.progress = percentThrough.toInt()
                break
            }
        }
    }

    private fun calculatePercentToNinetyNine(){
        var percentThrough = roundOffDecimal(skill.xp.toDouble() / 13034431.toDouble() * 100F)
        ninetyNinePercentText.text = percentThrough.toString() + "%"
        toNinetyNineBar.progress = percentThrough.toInt()
    }

    fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }

}