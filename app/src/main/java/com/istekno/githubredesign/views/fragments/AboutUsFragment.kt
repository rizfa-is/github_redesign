package com.istekno.githubredesign.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R

class AboutUsFragment(private val navigationView : NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu?.findItem(R.id.act_listOption)?.isVisible = false
        actionBar.menu?.findItem(R.id.act_favorite)?.isVisible = false
        actionBar.menu?.findItem(R.id.act_search)?.isVisible = false
        actionBar.title = resources.getString(R.string.about_us)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.aboutUs_nav_drawer2)
    }
}