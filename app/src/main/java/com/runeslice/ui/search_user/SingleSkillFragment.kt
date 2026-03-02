package com.runeslice.ui.search_user

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.runeslice.MyApplication
import com.runeslice.databinding.FragmentSingleSkillBinding
import com.runeslice.dataclass.Skill
import com.runeslice.util.XpCalculator
import java.util.Locale

class SingleSkillFragment : Fragment() {

    private var _binding: FragmentSingleSkillBinding? = null
    private val binding get() = _binding!!

    private lateinit var skill: Skill
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleSkillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            skill = bundle.getParcelable("skill")!!
            position = bundle.getInt("position")
        }

        prepareLayout()
    }

    private fun prepareLayout() {
        binding.apply {
            singleSkillIcon.setImageResource(MyApplication.skillImgs[position])
            singleSkillIcon.transitionName = "${skill.name.lowercase(Locale.ROOT)}Img"
            skillText.text = skill.name
            skillLevelText.text = skill.level.toString()
            skillXPText.text = "%,d".format(skill.xp)
            skillRankText.text = "%,d".format(skill.rank)

            if (skill.name != "Overall") {
                updateProgress(nextLevelPercentText, toNextLevelBar,
                    XpCalculator.getPercentThroughLevel(skill.xp))

                updateProgress(nextTenLevelPercentText, toNextMultipleOfTenBar,
                    XpCalculator.getPercentToNextTen(skill.xp))

                updateProgress(ninetyNinePercentText, toNinetyNineBar,
                    XpCalculator.getPercentToNinetyNine(skill.xp))
            } else {
                prepareOverall()
            }
        }
    }

    private fun prepareOverall() {
        binding.apply {
            toNinetyNineSection.visibility = View.INVISIBLE
            toNextLevelText.text = "To Max Level:"

            val levelPercent = XpCalculator.getOverallLevelPercent(skill.level)
            updateProgress(nextLevelPercentText, toNextLevelBar, levelPercent)

            toNextMultipleOfTenText.text = "To Max XP:"
            val xpPercent = XpCalculator.getOverallXpPercent(skill.xp.toLong())
            updateProgress(nextTenLevelPercentText, toNextMultipleOfTenBar, xpPercent)
        }
    }

    private fun updateProgress(textView: TextView, progressBar: ProgressBar, percent: Double) {
        textView.text = "$percent%"
        progressBar.progress = percent.toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}