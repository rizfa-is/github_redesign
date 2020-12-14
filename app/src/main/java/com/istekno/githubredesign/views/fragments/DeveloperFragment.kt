package com.istekno.githubredesign.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.R.layout
import com.istekno.githubredesign.adapters.developerfragment.ListDeveloperAdapter
import com.istekno.githubredesign.models.DeveloperList
import com.istekno.githubredesign.helpers.RecyclerViewMode
import com.istekno.githubredesign.helpers.ResponseAPI
import com.istekno.githubredesign.helpers.SearchDeveloperView
import com.istekno.githubredesign.viewmodels.BaseViewModel
import kotlinx.android.synthetic.main.fragment_developer.*

open class DeveloperFragment(private val navigationView: NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    private lateinit var baseViewModel: BaseViewModel
    private lateinit var developerAdapter: ListDeveloperAdapter

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu.findItem(R.id.act_listOption).isVisible = true
        actionBar.menu.findItem(R.id.act_favorite).isVisible = true
        actionBar.title = resources.getString(R.string.developer)

        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_developer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.developer_nav_drawer)
        val recyclerViewMode = RecyclerViewMode()
        val sv = view.findViewById<SearchView>(R.id.editCity_developer_list)
        initInheritance()

        recyclerViewMode.getRecyclerView(this, viewLifecycleOwner, sv, progressBar_developer_list, 0, context, rv_developer)
        actionMenuListener()
    }

    private fun initInheritance() {

    }

    private fun actionMenuListener() {
        actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fab_list -> {

                    Toast.makeText(context, "Select List", Toast.LENGTH_SHORT).show()
                }
                R.id.fab_grid -> {

                    Toast.makeText(context, "Select Grid", Toast.LENGTH_SHORT).show()
                }
                R.id.fab_cardView -> {

                    Toast.makeText(context, "Select CardView", Toast.LENGTH_SHORT).show()
                }
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