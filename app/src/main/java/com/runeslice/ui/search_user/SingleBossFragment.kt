package com.runeslice.ui.search_user

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.runeslice.MyApplication
import com.runeslice.databinding.FragmentSingleBossBinding
import com.runeslice.dataclass.Boss

class SingleBossFragment : Fragment() {

    private lateinit var bossIcon: ImageView
    private lateinit var bossText: TextView
    private lateinit var bossTextKc : TextView
    private lateinit var bossRankText : TextView

    private var _binding: FragmentSingleBossBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null

    lateinit var boss: Boss
    var bossID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleBossBinding.inflate(inflater, container, false)

        boss = requireArguments().getParcelable("boss")!!
        bossID = requireArguments().getInt("bossID")!!

        bossIcon = binding.singleSkillIcon
        bossText = binding.skillText
        bossTextKc = binding.skillXPText
        bossRankText = binding.skillRankText

        prepareLayout()

        sharedElementEnterTransition = TransitionInflater.from(this.context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(this.context).inflateTransition(android.R.transition.move)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

    private fun prepareLayout(){
        bossIcon.setImageResource(MyApplication.bossImgs[bossID])
        bossIcon.transitionName = boss.name.toLowerCase() +"Img"
        bossText.text = boss.name
        bossTextKc.text = boss.num.toString()
        bossRankText.text = "%,d".format(boss.rank)
    }
}