package com.istekno.githubredesign.helpers

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.adapters.developerfragment.ListDeveloperAdapter
import com.istekno.githubredesign.models.DeveloperList
import com.istekno.githubredesign.viewmodels.BaseViewModel
import com.istekno.githubredesign.views.activity.DeveloperDetailActivity
import com.istekno.githubredesign.views.fragments.DeveloperFragment

class RecyclerViewMode {

    private lateinit var baseViewModel: BaseViewModel

    fun getRecyclerView(owner: ViewModelStoreOwner, ownerLC: LifecycleOwner, searchView: android.widget.SearchView, progressBarID: View, position: Int, context: Context?, recyclerView: RecyclerView) {
        checkLayout(owner, ownerLC, searchView, progressBarID, position, context, recyclerView)
    }

    private fun checkLayout(owner: ViewModelStoreOwner, ownerLC: LifecycleOwner, searchView: android.widget.SearchView, progressBarID: View, position: Int, context: Context?, recyclerView: RecyclerView) {
        val responseAPI = ResponseAPI()
        val searchDeveloperView = SearchDeveloperView()
        baseViewModel = BaseViewModel()

        when (position) {
            0 -> {
                recyclerView.apply {
                    val developerAdapter = ListDeveloperAdapter(object : ListDeveloperAdapter.OnItemClickCallback {
                        override fun onItemClicked(developerList: DeveloperList) {
                            showActionClickCallback(developerList, context)
                        }
                    })

                    developerAdapter.notifyDataSetChanged()
                    layoutManager = LinearLayoutManager(context)
                    adapter = developerAdapter

                    baseViewModel = ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory()).get(BaseViewModel::class.java)
                    baseViewModel.setListDeveloperByPass(true, false)
                    searchDeveloperView.searchByUsername(searchView, progressBarID, responseAPI, baseViewModel)
                    baseViewModel.getListDeveloper().observe(ownerLC, Observer { listItems ->
                        if (listItems != null) {
                            developerAdapter.setData(listItems)
                            responseAPI.showLoadingDeveloperFragment(progressBarID, false)
                        }
                    })
                }
            }
            1 -> {
//                recyclerView.apply {
//                    listAdapter.notifyDataSetChanged()
//                    layoutManager = GridLayoutManager(context, 2)
//                    adapter = listAdapter
//                }
            }
            2 -> {

            }
        }
    }

    private fun rvAdapter() {

    }

    fun showActionClickCallback(developerDetail: DeveloperList, context: Context?, view: View) {
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

    fun showActionClickCallback(developerList: DeveloperList, context: Context?) {
        val intent = Intent(context, DeveloperDetailActivity::class.java)
        intent.putExtra(DeveloperFragment.INTENT_PARCELABLE, developerList)
        context?.startActivity(intent)
    }
}