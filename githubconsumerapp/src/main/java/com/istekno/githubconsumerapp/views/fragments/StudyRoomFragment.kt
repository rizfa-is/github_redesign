package com.istekno.githubconsumerapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.istekno.githubconsumerapp.R

class StudyRoomFragment(private val navigationView : NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu?.findItem(R.id.act_listOption)?.isVisible = false
        actionBar.menu?.findItem(R.id.act_favorite)?.isVisible = true
        actionBar.title = resources.getString(R.string.study_room)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.studyRoom_nav_drawer)

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
}