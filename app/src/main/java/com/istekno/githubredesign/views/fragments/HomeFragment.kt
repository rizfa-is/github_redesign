package com.istekno.githubredesign.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.helpers.RecyclerViewMode
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_card_home_explore_challenges.*
import kotlinx.android.synthetic.main.item_card_home_explore_developer.*
import kotlinx.android.synthetic.main.item_card_home_explore_studyroom.*

class HomeFragment(private val navigationView : NavigationView, private val actionBar: Toolbar) : Fragment(), View.OnClickListener {

    companion object {
        private val listImage = arrayOf(
            "https://static.vecteezy.com/system/resources/previews/000/228/437/non_2x/female-developer-vector-illustration.jpg",
            "https://img.freepik.com/free-vector/man-woman-business-reward-satisfaction-employee_159757-33.jpg?size=626&ext=jpg",
            "https://img.freepik.com/free-vector/students-studying-textbooks_74855-5294.jpg?size=626&ext=jpg")
    }

    private lateinit var recyclerViewMode: RecyclerViewMode

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu?.findItem(R.id.act_listOption)?.isVisible = false
        actionBar.menu?.findItem(R.id.act_favorite)?.isVisible = true
        actionBar.title = resources.getString(R.string.home)


        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.home_nav_drawer)
        recyclerViewMode = RecyclerViewMode()

        explore_developer_more.setOnClickListener(this)
        explore_challenges_more.setOnClickListener(this)
        explore_studyroom_more.setOnClickListener(this)

        recyclerViewMode.getRecyclerView(this, viewLifecycleOwner, progressBar_home_list, context, rv_most_popular)
        setActionExploreContent(view.context)
        actionBarMenuListener()
    }

    private fun setActionExploreContent(context: Context) {
        Glide.with(context).load(listImage[0]).into(explore_developer_img)
        Glide.with(context).load(listImage[1]).into(explore_challenges_img)
        Glide.with(context).load(listImage[2]).into(explore_studyroom_img)
    }

    private fun actionBarMenuListener() {
        actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.act_favorite -> {
                    val mFragmentManager = fragmentManager
                    mFragmentManager?.beginTransaction()?.apply {
                        replace(R.id.frame_layout, FavoriteFragment(navigationView, actionBar), FavoriteFragment::class.java.simpleName)
                        commit()
                    }
                }
            }
            false
        }
    }

    override fun onClick(view: View) {
        recyclerViewMode = RecyclerViewMode()

        recyclerViewMode.getActionClickCallback(view, navigationView, actionBar, fragmentManager)
    }
}