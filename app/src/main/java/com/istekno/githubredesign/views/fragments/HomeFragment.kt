package com.istekno.githubredesign.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.views.activity.DeveloperDetailActivity
import com.istekno.githubredesign.adapters.homefragment.CardViewExploreContentAdapter
import com.istekno.githubredesign.adapters.homefragment.CardViewMostPopularAdapter
import com.istekno.githubredesign.databases.BaseAPI
import com.istekno.githubredesign.models.Content
import com.istekno.githubredesign.models.DeveloperList
import com.istekno.githubredesign.databases.MainData
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment(private val navigationView : NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    private val listMostPopular = ArrayList<DeveloperList>()
    private val listExploreContent = ArrayList<Content>()
    private val getAPI = BaseAPI()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu?.findItem(R.id.act_listOption)?.isVisible = false
        actionBar.menu?.findItem(R.id.act_favorite)?.isVisible = true
        actionBar.menu?.findItem(R.id.act_search)?.isVisible = false
        actionBar.title = resources.getString(R.string.home)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.home_nav_drawer)
        val url ="https://api.github.com/users"

        showRecycleListExploreContent(view)
        actionBarMenuListener()
    }

    private fun showActionClickCallbackPopular(developerList: DeveloperList) {
        val intent = Intent(this.context, DeveloperDetailActivity::class.java)
        intent.putExtra(DeveloperFragment.INTENT_PARCELABLE, developerList)
        startActivity(intent)
    }

    private fun showActionClickCallbackExplore(content: Content) {
        var fragmentClass = Fragment()
        var fragmentTAG : String? = null

        when(content.name) {
            R.string.developer -> {
                fragmentClass = DeveloperFragment(navigationView, actionBar)
                fragmentTAG = DeveloperFragment::class.java.simpleName
            }

            R.string.challenges -> {
                fragmentClass = ChallengesFragment(navigationView, actionBar)
                fragmentTAG = ChallengesFragment::class.java.simpleName
            }

            R.string.study_room -> {
                fragmentClass = StudyRoomFragment(navigationView, actionBar)
                fragmentTAG = StudyRoomFragment::class.java.simpleName
            }
        }

        val mFragmentManager = fragmentManager
        mFragmentManager?.beginTransaction()?.apply {
            replace(R.id.frame_layout, fragmentClass, fragmentTAG)
            commit()
        }
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

    private fun showRecycleListMostPopular(view: View) {
        listMostPopular.sortBy { it.username }
        rv_most_popular.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL,false)
            adapter = CardViewMostPopularAdapter(listMostPopular, object : CardViewMostPopularAdapter.OnItemClickCallback {
                override fun onItemClicked(developerList: DeveloperList) {
                    showActionClickCallbackPopular(developerList)
                }
            })
        }
    }

    private fun showRecycleListExploreContent(view: View) {
        listExploreContent.addAll(MainData.listDataExploreContent)
        rv_explore_content.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

            val cardAdapter = CardViewExploreContentAdapter(listExploreContent, object : CardViewExploreContentAdapter.OnItemClickCallback {
                override fun onItemClicked(content: Content) {
                    showActionClickCallbackExplore(content)
                }
            })
            adapter = cardAdapter
        }
    }
}