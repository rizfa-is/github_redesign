package com.istekno.githubredesign.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.R.layout
import com.istekno.githubredesign.databases.BaseAPI
import com.istekno.githubredesign.models.DeveloperList
import com.istekno.githubredesign.helpers.RecyclerViewMode
import com.istekno.githubredesign.helpers.ResponseAPI
import com.istekno.githubredesign.viewmodels.BaseViewModel
import kotlinx.android.synthetic.main.fragment_developer.*

open class DeveloperFragment(private val navigationView: NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    private lateinit var baseViewModel: BaseViewModel
    private lateinit var recyclerViewMode: RecyclerViewMode
    private lateinit var baseAPI: BaseAPI
    private lateinit var responseAPI: ResponseAPI
    private val listDeveloper = ArrayList<DeveloperList>()

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu.findItem(R.id.act_listOption).isVisible = true
        actionBar.menu.findItem(R.id.act_favorite).isVisible = true
        actionBar.menu.findItem(R.id.act_search).isVisible = true
        actionBar.title = resources.getString(R.string.developer)

        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_developer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.developer_nav_drawer)
        initInheritance()

        val url = "https://api.github.com/users"
        responseAPI.showLoadingDeveloperFragment(progressBar_developer_list, true)
        baseAPI.getDeveloperListData(listDeveloper, true, url, false) {
            if (listDeveloper.isNotEmpty()) {
                recyclerViewMode.showRecyclerList(context, rv_developer, listDeveloper)
                actionMenuListener(listDeveloper)
                responseAPI.showLoadingDeveloperFragment(progressBar_developer_list, false)
            }
        }
    }

    private fun initInheritance() {
        recyclerViewMode = RecyclerViewMode()
        baseViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(BaseViewModel::class.java)
        baseAPI = BaseAPI()
        responseAPI = ResponseAPI()
    }

    private fun actionMenuListener(list: ArrayList<DeveloperList>) {
        actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fab_list -> {
                    recyclerViewMode.showRecyclerList(context, rv_developer, list)
                    Toast.makeText(context, "Select List", Toast.LENGTH_SHORT).show()
                }
                R.id.fab_grid -> {
                    recyclerViewMode.showRecyclerGrid(context, rv_developer, list)
                    Toast.makeText(context, "Select Grid", Toast.LENGTH_SHORT).show()
                }
                R.id.fab_cardView -> {
                    recyclerViewMode.showRecyclerCard(context, rv_developer, list)
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