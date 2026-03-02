package com.runeslice.ui.search_user

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.runeslice.MyApplication
import com.runeslice.databinding.FragmentSingleBossBinding
import com.runeslice.dataclass.Boss
import java.util.Locale

class SingleBossFragment : Fragment() {

    private var _binding: FragmentSingleBossBinding? = null
    private val binding get() = _binding!!

    private lateinit var boss: Boss
    private var bossID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleBossBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            boss = it.getParcelable("boss")!!
            bossID = it.getInt("bossID")
        }

        prepareLayout()
    }

    private fun prepareLayout() {
        binding.apply {
            singleSkillIcon.setImageResource(MyApplication.bossImgs[bossID])
            singleSkillIcon.transitionName = "${boss.name.lowercase(Locale.ROOT)}Img"

            skillText.text = boss.name
            skillXPText.text = boss.num.toString()
            skillRankText.text = "%,d".format(boss.rank)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}