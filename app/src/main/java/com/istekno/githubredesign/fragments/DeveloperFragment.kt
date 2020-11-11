package com.istekno.githubredesign.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
import com.istekno.githubredesign.data.Developer
import com.istekno.githubredesign.data.MainData
import kotlinx.android.synthetic.main.fragment_developer.*

class DeveloperFragment(private val navigationView: NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    private val listDeveloper = ArrayList<Developer>()

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu.findItem(R.id.act_listOption).isVisible = true
        actionBar.menu?.findItem(R.id.act_favorite)?.isVisible = true
        actionBar.title = "Developer"

        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_developer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.developer_nav_drawer)

        listDeveloper.addAll(MainData.listDataDeveloper)
        showRecyclerList()

        actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fab_list -> {
                    showRecyclerList()
                }
                R.id.fab_grid -> {
                    showRecyclerGrid()
                }
                R.id.fab_cardView -> {
                    showRecyclerCard()
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

    private fun showRecyclerList() {
        rv_developer.apply {
            layoutManager = LinearLayoutManager(view?.context)
            adapter = ListDeveloperAdapter(listDeveloper, object : ListDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developer: Developer) {
                    showActionClickCallback(developer)
                }
            })
        }
    }

    private fun showRecyclerGrid() {
        rv_developer.apply {
            layoutManager = GridLayoutManager(view?.context, 2)
            adapter = GridDeveloperAdapter(listDeveloper, object : GridDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developer: Developer) {
                    showActionClickCallback(developer)
                }
            })
        }
    }

    private fun showRecyclerCard() {
        rv_developer.apply {
            layoutManager = LinearLayoutManager(view?.context)
            adapter = CardViewDeveloperAdapter(listDeveloper, object : CardViewDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developer: Developer, itemView: View) {
                    showActionClickCallbackCard(developer, itemView)
                }
            })
        }
    }

    private fun showActionClickCallback(developer: Developer) {
        val intent = Intent(this.context, DeveloperDetailActivity::class.java)
        intent.putExtra(INTENT_PARCELABLE, developer)
        startActivity(intent)
    }

    private fun showActionClickCallbackCard(developer: Developer, view: View) {
        when (view.id) {
            R.id.item_card_developer_btn_set_share -> {
                val uriString = "Go to my Github's Profile and let's make a change by code the future\n"+"https://github.com/${developer.username}"
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
                intent.putExtra(INTENT_PARCELABLE, developer)
                startActivity(intent)
            }
        }
    }
}