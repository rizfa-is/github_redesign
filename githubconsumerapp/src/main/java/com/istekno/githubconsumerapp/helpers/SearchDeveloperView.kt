package com.istekno.githubconsumerapp.helpers

import android.view.View
import com.istekno.githubconsumerapp.viewmodels.BaseViewModel

class SearchDeveloperView {

    fun searchByUsername(searchView: android.widget.SearchView, progressBarID: View, responseAPI: ResponseAPI, baseViewModel: BaseViewModel) {
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.isEmpty()) return false

                    responseAPI.showLoadingDeveloperFragment(progressBarID, true)
                    baseViewModel.setListDeveloper(p0, false, false)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.isEmpty()) return false

                    responseAPI.showLoadingDeveloperFragment(progressBarID, true)
                    baseViewModel.setListDeveloper(p0, false, false)
                }
                return false
            }
        })
    }
}