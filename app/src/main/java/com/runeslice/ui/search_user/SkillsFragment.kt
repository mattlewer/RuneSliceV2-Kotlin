package com.runeslice.ui.search_user

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.gson.Gson
import com.runeslice.MyApplication
import com.runeslice.R
import com.runeslice.databinding.FragmentSkillsBinding
import com.runeslice.dataclass.User2


class SkillsFragment : Fragment(){

    private var _binding: FragmentSkillsBinding? = null
    private val binding get() = _binding!!
    var navController : NavController? = null
    lateinit var currentUser: User2

    private lateinit var cards: MutableList<CardView>
    private lateinit var textViewsTitles: MutableList<TextView>
    private lateinit var textViewsSubtext: MutableList<TextView>
    private lateinit var imgViews: MutableList<ImageView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSkillsBinding.inflate(inflater, container, false)

        loadCurrentUser()
        setBindings()
        prepareGridLayout()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)

        val grid: GridLayout = binding.skillGridLayout
        val childCount: Int = grid.getChildCount()

        for (i in 0 until childCount) {
            grid.getChildAt(i).setOnClickListener {
                var bundle = Bundle()
                var extras = FragmentNavigatorExtras(
                    imgViews[i] to currentUser.skills[i].name.toLowerCase() + "Img"
                )
                navController!!.navigate(
                    R.id.singleSkillFragment,
                    bundle.apply {
                        putParcelable("skill", currentUser.skills[i])
                        putInt("position", i)
                    },
                    null,
                    extras
                )
            }
        }
    }

    fun loadCurrentUser(){
        val gson = Gson()
        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val userJson : String? = sharedPrefs!!.getString("user", "")
        currentUser = gson.fromJson(userJson, User2::class.java)
    }


    fun prepareGridLayout(){
        for (skill in 0..currentUser.skills.size-1){
            imgViews[skill].setImageResource(MyApplication.skillImgs[skill])
            textViewsTitles[skill].text = currentUser.skills[skill].level.toString()
//            textViewsSubtext[skill].text = currentUser.skills[skill].name
        }
    }


    fun setBindings(){

        cards = mutableListOf(
            binding.overallCard.include.skillImageCard,
            binding.attackCard.include.skillImageCard,
            binding.defenceCard.include.skillImageCard,
            binding.strengthCard.include.skillImageCard,
            binding.hitpointsCard.include.skillImageCard,
            binding.rangedCard.include.skillImageCard,
            binding.prayerCard.include.skillImageCard,
            binding.magicCard.include.skillImageCard,
            binding.cookingCard.include.skillImageCard,
            binding.woodcuttingCard.include.skillImageCard,
            binding.fletchingCard.include.skillImageCard,
            binding.fishingCard.include.skillImageCard,
            binding.firemakingCard.include.skillImageCard,
            binding.craftingCard.include.skillImageCard,
            binding.smithingCard.include.skillImageCard,
            binding.miningCard.include.skillImageCard,
            binding.herbloreCard.include.skillImageCard,
            binding.agilityCard.include.skillImageCard,
            binding.thievingCard.include.skillImageCard,
            binding.slayerCard.include.skillImageCard,
            binding.farmingCard.include.skillImageCard,
            binding.runecraftingCard.include.skillImageCard,
            binding.hunterCard.include.skillImageCard,
            binding.constructionCard.include.skillImageCard
        )
        textViewsTitles = mutableListOf(
                binding.overallCard.cardTitle,
                binding.attackCard.cardTitle,
                binding.defenceCard.cardTitle,
                binding.strengthCard.cardTitle,
                binding.hitpointsCard.cardTitle,
                binding.rangedCard.cardTitle,
                binding.prayerCard.cardTitle,
                binding.magicCard.cardTitle,
                binding.cookingCard.cardTitle,
                binding.woodcuttingCard.cardTitle,
                binding.fletchingCard.cardTitle,
                binding.fishingCard.cardTitle,
                binding.firemakingCard.cardTitle,
                binding.craftingCard.cardTitle,
                binding.smithingCard.cardTitle,
                binding.miningCard.cardTitle,
                binding.herbloreCard.cardTitle,
                binding.agilityCard.cardTitle,
                binding.thievingCard.cardTitle,
                binding.slayerCard.cardTitle,
                binding.farmingCard.cardTitle,
                binding.runecraftingCard.cardTitle,
                binding.hunterCard.cardTitle,
                binding.constructionCard.cardTitle
        )

        textViewsSubtext = mutableListOf(
                binding.overallCard.cardSubtext,
                binding.attackCard.cardSubtext,
                binding.defenceCard.cardSubtext,
                binding.strengthCard.cardSubtext,
                binding.hitpointsCard.cardSubtext,
                binding.rangedCard.cardSubtext,
                binding.prayerCard.cardSubtext,
                binding.magicCard.cardSubtext,
                binding.cookingCard.cardSubtext,
                binding.woodcuttingCard.cardSubtext,
                binding.fletchingCard.cardSubtext,
                binding.fishingCard.cardSubtext,
                binding.firemakingCard.cardSubtext,
                binding.craftingCard.cardSubtext,
                binding.smithingCard.cardSubtext,
                binding.miningCard.cardSubtext,
                binding.herbloreCard.cardSubtext,
                binding.agilityCard.cardSubtext,
                binding.thievingCard.cardSubtext,
                binding.slayerCard.cardSubtext,
                binding.farmingCard.cardSubtext,
                binding.runecraftingCard.cardSubtext,
                binding.hunterCard.cardSubtext,
                binding.constructionCard.cardSubtext
        )
        for(i in 0..textViewsSubtext.size-1){
            textViewsSubtext[i].visibility = View.GONE
        }

        imgViews = mutableListOf(
                binding.overallCard.include.circularImageSmallImg,
                binding.attackCard.include.circularImageSmallImg,
                binding.defenceCard.include.circularImageSmallImg,
                binding.strengthCard.include.circularImageSmallImg,
                binding.hitpointsCard.include.circularImageSmallImg,
                binding.rangedCard.include.circularImageSmallImg,
                binding.prayerCard.include.circularImageSmallImg,
                binding.magicCard.include.circularImageSmallImg,
                binding.cookingCard.include.circularImageSmallImg,
                binding.woodcuttingCard.include.circularImageSmallImg,
                binding.fletchingCard.include.circularImageSmallImg,
                binding.fishingCard.include.circularImageSmallImg,
                binding.firemakingCard.include.circularImageSmallImg,
                binding.craftingCard.include.circularImageSmallImg,
                binding.smithingCard.include.circularImageSmallImg,
                binding.miningCard.include.circularImageSmallImg,
                binding.herbloreCard.include.circularImageSmallImg,
                binding.agilityCard.include.circularImageSmallImg,
                binding.thievingCard.include.circularImageSmallImg,
                binding.slayerCard.include.circularImageSmallImg,
                binding.farmingCard.include.circularImageSmallImg,
                binding.runecraftingCard.include.circularImageSmallImg,
                binding.hunterCard.include.circularImageSmallImg,
                binding.constructionCard.include.circularImageSmallImg
        )
        for(x in 0..imgViews.size-1){
            imgViews[x].transitionName = currentUser.skills[x].name.toLowerCase() + "Img"
        }

    }

}