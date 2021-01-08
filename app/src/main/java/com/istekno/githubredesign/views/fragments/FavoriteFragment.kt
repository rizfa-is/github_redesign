package com.istekno.githubredesign.views.fragments

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.istekno.githubredesign.R
import com.istekno.githubredesign.adapters.FavoriteListAdapter
import com.istekno.githubredesign.databinding.FragmentFavoriteBinding
import com.istekno.githubredesign.db.DatabaseContract.FavoriteColumn.Companion.CONTENT_URI
import com.istekno.githubredesign.entity.Favorite
import com.istekno.githubredesign.helpers.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFragment(private val navigationView : NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    companion object {
        const val EXTRA_STATE = "extra_state"
    }

    private lateinit var favoriteBinding: FragmentFavoriteBinding
    private lateinit var adapter: FavoriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu?.findItem(R.id.act_listOption)?.isVisible = false
        actionBar.menu?.findItem(R.id.act_favorite)?.isVisible = false
        actionBar.title = resources.getString(R.string.favorites)

        favoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return favoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.favorit_nav_drawer2)

        favoriteBinding.rvFavorite.layoutManager = LinearLayoutManager(view.context)
        favoriteBinding.rvFavorite.setHasFixedSize(true)
        adapter = FavoriteListAdapter()
        favoriteBinding.rvFavorite.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadGithubAsync()
            }
        }

        activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadGithubAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorite = list
            }
        }
    }

    private fun loadGithubAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            favoriteBinding.progressBarFavorite.visibility = View.VISIBLE
            val deferredGithub = async(Dispatchers.IO) {
                val cursor = activity?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val github = deferredGithub.await()
            favoriteBinding.progressBarFavorite.visibility = View.GONE

            if (github.size > 0) {
                adapter.listFavorite = github
            } else {
                adapter.listFavorite = ArrayList()
                showSnackBarMessage("There's no data")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorite)
    }

    private fun showSnackBarMessage(msg: String) {
        Snackbar.make(favoriteBinding.rvFavorite, msg, Snackbar.LENGTH_SHORT).show()
    }
}