package com.istekno.githubconsumerapp.helpers

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.istekno.githubconsumerapp.R
import com.istekno.githubconsumerapp.adapters.developerfragment.CardViewDeveloperAdapter
import com.istekno.githubconsumerapp.adapters.developerfragment.GridDeveloperAdapter
import com.istekno.githubconsumerapp.adapters.developerfragment.ListDeveloperAdapter
import com.istekno.githubconsumerapp.adapters.homefragment.CardViewMostPopularAdapter
import com.istekno.githubconsumerapp.entity.Favorite
import com.istekno.githubconsumerapp.models.DeveloperDetail
import com.istekno.githubconsumerapp.viewmodels.BaseViewModel
import com.istekno.githubconsumerapp.views.activity.DeveloperDetailActivity
import com.istekno.githubconsumerapp.views.fragments.ChallengesFragment
import com.istekno.githubconsumerapp.views.fragments.DeveloperFragment
import com.istekno.githubconsumerapp.views.fragments.StudyRoomFragment

class RecyclerViewMode {

    private lateinit var baseViewModel: BaseViewModel
    private lateinit var responseAPI: ResponseAPI
    private lateinit var searchDeveloperView: SearchDeveloperView

    fun getRecyclerView(owner: ViewModelStoreOwner, ownerLC: LifecycleOwner, searchView: android.widget.SearchView, progressBarID: View, position: Int, context: Context?, recyclerView: RecyclerView) {
        checkLayout(owner, ownerLC, searchView, progressBarID, position, context, recyclerView)
    }

    fun getRecyclerView(owner: ViewModelStoreOwner, ownerLC: LifecycleOwner, progressBarID: View, context: Context?, recyclerView: RecyclerView) {
        homeMostPopularLayout(owner, ownerLC, progressBarID, context, recyclerView)
    }

    fun getActionClickCallback(view: View, navigationView: NavigationView, actionBar: Toolbar, fragmentManager: FragmentManager?) {
        showActionClickCallbackExplore(view, navigationView, actionBar, fragmentManager)
    }

    private fun homeMostPopularLayout(owner: ViewModelStoreOwner, ownerLC: LifecycleOwner, progressBarID: View, context: Context?, recyclerView: RecyclerView) {
        responseAPI = ResponseAPI()
        searchDeveloperView = SearchDeveloperView()
        baseViewModel = BaseViewModel()

        recyclerView.apply {
            val developerAdapter = CardViewMostPopularAdapter(object : CardViewMostPopularAdapter.OnItemClickCallback {
                override fun onItemClicked(developerDetail: DeveloperDetail) {
                    showActionClickCallback(developerDetail, context)
                }
            })

            developerAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = developerAdapter

            baseViewModel = ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory()).get(BaseViewModel::class.java)
            baseViewModel.setListDeveloperByPass(true, true)
            baseViewModel.getListDeveloper().observe(ownerLC, Observer { listItems ->
                if (listItems != null) {
                    developerAdapter.setData(listItems)
                    responseAPI.showLoadingDeveloperFragment(progressBarID, false)
                }
            })
        }
    }

    private fun checkLayout(owner: ViewModelStoreOwner, ownerLC: LifecycleOwner, searchView: android.widget.SearchView, progressBarID: View, position: Int, context: Context?, recyclerView: RecyclerView) {
        responseAPI = ResponseAPI()
        searchDeveloperView = SearchDeveloperView()
        baseViewModel = BaseViewModel()

        when (position) {
            0 -> {
                recyclerView.apply {
                    val developerAdapter = ListDeveloperAdapter(object : ListDeveloperAdapter.OnItemClickCallback {
                        override fun onItemClicked(developerDetail: DeveloperDetail) {
                            showActionClickCallback(developerDetail, context)
                        }
                    })

                    developerAdapter.notifyDataSetChanged()
                    layoutManager = LinearLayoutManager(context)
                    adapter = developerAdapter

                    baseViewModel = ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory()).get(BaseViewModel::class.java)
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
                recyclerView.apply {
                    val developerAdapter = GridDeveloperAdapter(object : GridDeveloperAdapter.OnItemClickCallback {
                        override fun onItemClicked(developerDetail: DeveloperDetail) {
                            showActionClickCallback(developerDetail, context)
                        }
                    })

                    developerAdapter.notifyDataSetChanged()
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = developerAdapter

                    baseViewModel = ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory()).get(BaseViewModel::class.java)
                    searchDeveloperView.searchByUsername(searchView, progressBarID, responseAPI, baseViewModel)
                    baseViewModel.getListDeveloper().observe(ownerLC, Observer { listItems ->
                        if (listItems != null) {
                            developerAdapter.setData(listItems)
                            responseAPI.showLoadingDeveloperFragment(progressBarID, false)
                        }
                    })
                }
            }

            2 -> {
                recyclerView.apply {
                    val developerAdapter = CardViewDeveloperAdapter(object : CardViewDeveloperAdapter.OnItemClickCallback {
                        override fun onItemClicked(developerDetail: DeveloperDetail, itemView: View) {
                            showActionClickCallback(developerDetail, context, itemView)
                        }
                    })

                    developerAdapter.notifyDataSetChanged()
                    layoutManager = LinearLayoutManager(context)
                    adapter = developerAdapter

                    baseViewModel = ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory()).get(BaseViewModel::class.java)
                    searchDeveloperView.searchByUsername(searchView, progressBarID, responseAPI, baseViewModel)
                    baseViewModel.getListDeveloper().observe(ownerLC, Observer { listItems ->
                        if (listItems != null) {
                            developerAdapter.setData(listItems)
                            responseAPI.showLoadingDeveloperFragment(progressBarID, false)
                        }
                    })
                }
            }

            3 -> {
                recyclerView.apply {
                    val developerAdapter = ListDeveloperAdapter(object : ListDeveloperAdapter.OnItemClickCallback {
                        override fun onItemClicked(developerDetail: DeveloperDetail) {
                            showActionClickCallback(developerDetail, context)
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
        }
    }

    private fun showActionClickCallback(developerDetail: DeveloperDetail, context: Context?, view: View) {
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

    private fun showActionClickCallback(developerDetail: DeveloperDetail, context: Context?) {
        val intent = Intent(context, DeveloperDetailActivity::class.java)
        intent.putExtra(DeveloperFragment.INTENT_PARCELABLE, developerDetail)
        context?.startActivity(intent)
    }

    private fun showActionClickCallbackExplore(view: View, navigationView: NavigationView, actionBar: Toolbar, fragmentManager: FragmentManager?) {
        var fragmentClass = Fragment()
        var fragmentTAG : String? = null

        when(view.id) {
            R.id.explore_developer_more -> {
                fragmentClass = DeveloperFragment(navigationView, actionBar)
                fragmentTAG = DeveloperFragment::class.java.simpleName
            }

            R.id.explore_challenges_more -> {
                fragmentClass = ChallengesFragment(navigationView, actionBar)
                fragmentTAG = ChallengesFragment::class.java.simpleName
            }

            R.id.explore_studyroom_more -> {
                fragmentClass = StudyRoomFragment(navigationView, actionBar)
                fragmentTAG = StudyRoomFragment::class.java.simpleName
            }
        }

        val mFragmentManager = fragmentManager
        mFragmentManager?.beginTransaction()?.apply {
            replace(R.id.frame_layout, fragmentClass, fragmentTAG)
            commit()
        }
    }
}