package com.istekno.githubredesign.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.R.layout
import com.istekno.githubredesign.views.activity.DeveloperDetailActivity
import com.istekno.githubredesign.adapters.developerfragment.CardViewDeveloperAdapter
import com.istekno.githubredesign.adapters.developerfragment.GridDeveloperAdapter
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

        actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fab_list -> {
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