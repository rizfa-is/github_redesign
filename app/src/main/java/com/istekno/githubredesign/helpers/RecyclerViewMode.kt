package com.istekno.githubredesign.helpers

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.adapters.developerfragment.CardViewDeveloperAdapter
import com.istekno.githubredesign.adapters.developerfragment.GridDeveloperAdapter
import com.istekno.githubredesign.views.activity.DeveloperDetailActivity
import com.istekno.githubredesign.adapters.developerfragment.ListDeveloperAdapter
import com.istekno.githubredesign.views.fragments.DeveloperFragment
import com.istekno.githubredesign.models.DeveloperList

class RecyclerViewMode {

    fun showRecyclerList(context: Context?, recyclerView: RecyclerView, arrayList: ArrayList<DeveloperList>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val listAdapter = ListDeveloperAdapter(object : ListDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developerList: DeveloperList) {
                    showActionClickCallback(developerList, context)
                }
            })
            listAdapter.setData(arrayList)
            adapter = listAdapter
        }
    }

    fun showRecyclerGrid(context: Context?, recyclerView: RecyclerView, arrayList: ArrayList<DeveloperList>) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            val gridAdapter = GridDeveloperAdapter(object : GridDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developerList: DeveloperList) {
                    showActionClickCallback(developerList, context)
                }
            })
            gridAdapter.setData(arrayList)
            adapter = gridAdapter
        }
    }

    fun showRecyclerCard(context: Context?, recyclerView: RecyclerView, arrayList: ArrayList<DeveloperList>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val cardAdapter = CardViewDeveloperAdapter(object : CardViewDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developerList: DeveloperList, itemView: View) {
                    showActionClickCallback(developerList, context, itemView)
                }
            })
            cardAdapter.setData(arrayList)
            adapter = cardAdapter
        }
    }

    private fun showActionClickCallback(developerDetail: DeveloperList, context: Context?, view: View) {
        when (view.id) {
            R.id.item_card_developer_btn_set_share -> {
                val uriString = "Go to my Github's Profile and let's make a change by code the future\n"+"https://github.com/${developerDetail.username}"
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, uriString)
                    type = "text/plain"
                }
                val send = Intent.createChooser(intent, null)
                context?.startActivity(send)
            }

            R.id.item_card_developer_btn_set_detail -> {
                val intent = Intent(context, DeveloperDetailActivity::class.java)
                intent.putExtra(DeveloperFragment.INTENT_PARCELABLE, developerDetail)
                context?.startActivity(intent)
            }
        }
    }

    private fun showActionClickCallback(developerList: DeveloperList, context: Context?) {
        val intent = Intent(context, DeveloperDetailActivity::class.java)
        intent.putExtra(DeveloperFragment.INTENT_PARCELABLE, developerList)
        context?.startActivity(intent)
    }
}