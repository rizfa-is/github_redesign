package com.istekno.githubredesign.fragments

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.R.layout
import com.istekno.githubredesign.activity.DeveloperDetailActivity
import com.istekno.githubredesign.adapter.developerfragment.CardViewDeveloperAdapter
import com.istekno.githubredesign.adapter.developerfragment.GridDeveloperAdapter
import com.istekno.githubredesign.adapter.developerfragment.ListDeveloperAdapter
import com.istekno.githubredesign.api.API
import com.istekno.githubredesign.data.DeveloperDetail
import com.istekno.githubredesign.data.DeveloperList
import kotlinx.android.synthetic.main.fragment_developer.*

open class DeveloperFragment(private val navigationView: NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    private var listDeveloper = ArrayList<DeveloperList>()
    private val getAPI = API()

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

        searchByUsername(view)
    }

    private fun searchByUsername(view: View) {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = actionBar.menu.findItem(R.id.act_search).actionView as SearchView
        var url = "https://api.github.com/users"
        var empty = true

        getListData(1, view, url, true)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = "Input username"
        searchView.setBackgroundColor(Color.WHITE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query?.isEmpty()!!) {
                    url ="https://api.github.com/search/users?q=\"$query\""
                    empty = false
                }

                listDeveloper.clear()
                getListData(2, view, url, empty)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText?.isEmpty()!!) {
                    url ="https://api.github.com/search/users?q=\"$newText\""
                    empty = false
                }

                listDeveloper.clear()
                getListData(2, view, url, empty)
                return false
            }
        })
    }

    private fun getListData(position: Int, view: View, url: String, empty: Boolean) {
        if (position == 1) {
            getAPI.getDeveloperListData(view, progressBar_developer_list, true, listDeveloper, url, empty, false) { actionMenuListener(listDeveloper) }
        } else {
            getAPI.getDeveloperListData(view, progressBar_developer_list, false, listDeveloper, url, empty, true) { actionMenuListener(listDeveloper) }
        }
    }

    private fun showRecyclerList(list: ArrayList<DeveloperList>) {
        rv_developer.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListDeveloperAdapter(list, object : ListDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developerList: DeveloperList) {
                    showActionClickCallback(developerList)
                }
            })
        }
    }

    private fun showRecyclerGrid(list: ArrayList<DeveloperList>) {
        rv_developer.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = GridDeveloperAdapter(list, object : GridDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developerList: DeveloperList) {
                    showActionClickCallback(developerList)
                }
            })
        }
    }

    private fun showRecyclerCard(list: ArrayList<DeveloperList>) {
        rv_developer.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CardViewDeveloperAdapter(list, object : CardViewDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developerList: DeveloperList, itemView: View) {
                    showActionClickCallbackCard(developerList, itemView)
                }
            })
        }
    }

    private fun showActionClickCallback(developerDetail: DeveloperList) {
        val intent = Intent(this.context, DeveloperDetailActivity::class.java)
        intent.putExtra(INTENT_PARCELABLE, developerDetail)
        startActivity(intent)
    }

    private fun showActionClickCallbackCard(developerDetail: DeveloperList, view: View) {
        when (view.id) {
            R.id.item_card_developer_btn_set_share -> {
                val uriString = "Go to my Github's Profile and let's make a change by code the future\n"+"https://github.com/${developerDetail.username}"
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, uriString)
                    type = "text/plain"
                }
                val send = Intent.createChooser(intent, null)
                startActivity(send)
            }

            R.id.item_card_developer_btn_set_detail -> {
                val intent = Intent(this.context, DeveloperDetailActivity::class.java)
                intent.putExtra(INTENT_PARCELABLE, developerDetail)
                startActivity(intent)
            }
        }
    }

    private fun actionMenuListener(list: ArrayList<DeveloperList>) {
        showRecyclerList(list)

        actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fab_list -> {
                    showRecyclerList(list)
                    Toast.makeText(context, "Select List", Toast.LENGTH_SHORT).show()
                }
                R.id.fab_grid -> {
                    showRecyclerGrid(list)
                    Toast.makeText(context, "Select Grid", Toast.LENGTH_SHORT).show()
                }
                R.id.fab_cardView -> {
                    showRecyclerCard(list)
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