package com.istekno.githubredesign.helpers

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private fun showActionClickCallback(developerList: DeveloperList, context: Context?) {
        val intent = Intent(context, DeveloperDetailActivity::class.java)
        intent.putExtra(DeveloperFragment.INTENT_PARCELABLE, developerList)
        context?.startActivity(intent)
    }
}