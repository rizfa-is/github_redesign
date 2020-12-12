package com.istekno.githubredesign.view

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.istekno.githubredesign.R
import com.istekno.githubredesign.viewmodel.BaseViewModel

class SearchDeveloperView {

    private lateinit var mActivity: Activity
    private lateinit var responseAPI: ResponseAPI

    private fun searchByUsername(progressBarID: View, viewModel: BaseViewModel, actionBar: Toolbar) {
        mActivity = Activity()
        responseAPI = ResponseAPI()

        val searchManager = mActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = actionBar.menu.findItem(R.id.act_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(mActivity.componentName))
        searchView.queryHint = "Input username"
        searchView.setBackgroundColor(Color.WHITE)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isEmpty()!!) return false
                responseAPI.showLoadingDeveloperFragment(progressBarID,true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
    }
}