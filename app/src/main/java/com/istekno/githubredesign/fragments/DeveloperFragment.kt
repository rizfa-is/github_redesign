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
import com.istekno.githubredesign.data.Developer
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_developer.*
import org.json.JSONArray
import org.json.JSONObject

open class DeveloperFragment(private val navigationView: NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    private var listDeveloper = ArrayList<Developer>()

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

        searchByUsername(0)

        actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fab_list -> {
                    searchByUsername(0)
                }
                R.id.fab_grid -> {
                    searchByUsername(1)
                }
                R.id.fab_cardView -> {
                    searchByUsername(2)
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

    private fun searchByUsername(position: Int) {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = actionBar.menu.findItem(R.id.act_search).actionView as SearchView
        var url = "https://api.github.com/users"
        var empty = true

        getDeveloperListData(0, true, url, empty)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = "Input username"
        searchView.setBackgroundColor(Color.WHITE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isEmpty() == false) {
                    url ="https://api.github.com/search/users?q=\"$query\""
                    empty = false
                } else {
                    url ="https://api.github.com/users"
                    empty = true
                }

                listDeveloper.clear()
                getDeveloperListData(position, false, url, empty)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == false) {
                    url ="https://api.github.com/search/users?q=\"$newText\""
                    empty = false
                } else {
                    url ="https://api.github.com/users"
                    empty = true
                }

                listDeveloper.clear()
                getDeveloperListData(position, false, url, empty)
                return false
            }
        })
    }

    fun getDeveloperListData( switchNumber: Int, progressBar: Boolean, url: String, empty: Boolean) {

        if (progressBar == true) {
            progressBar_developer_list.visibility = View.VISIBLE
        }

        var loginId: String
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                progressBar_developer_list.visibility = View.INVISIBLE

                val res = responseBody?.let { String(it) }
                val result : JSONArray
                if (empty == true) {
                    result = JSONArray(res)
                } else {
                    result = JSONObject(res).getJSONArray("items")
                }

                try {
                    for (i in 0 until result.length()) {
                        val jsonObject = result.getJSONObject(i)
                        loginId = jsonObject.getString("login")
                        getDeveloperDetailData(loginId, switchNumber)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                progressBar_developer_list.visibility = View.INVISIBLE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDeveloperDetailData(loginId: String, switchNumber: Int) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com//users/$loginId"
        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val responsObject = JSONObject(result)


                    val avatar = responsObject.getString("avatar_url")
                    val name = responsObject.getString("name")
                    val company = responsObject.getString("company")
                    val location = responsObject.getString("location")
                    val username = responsObject.getString("login")
                    val repository = responsObject.getString("public_repos")
                    val follower = responsObject.getString("followers")
                    val following = responsObject.getString("following")

                    val developer = Developer()
                    developer.avatar = avatar
                    developer.name = name
                    developer.company = company
                    developer.location = location
                    developer.username = username
                    developer.repository = repository.toInt()
                    developer.follower = follower.toInt()
                    developer.following = following.toInt()

                    listDeveloper.add(developer)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                when (switchNumber) {
                    0 -> showRecyclerList(listDeveloper)
                    1 -> showRecyclerGrid(listDeveloper)
                    2 ->showRecyclerCard(listDeveloper)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRecyclerList(list: ArrayList<Developer>) {
        rv_developer.apply {
            layoutManager = LinearLayoutManager(view?.context)
            adapter = ListDeveloperAdapter(list, object : ListDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developer: Developer) {
                    showActionClickCallback(developer)
                }
            })
        }
    }

    private fun showRecyclerGrid(list: ArrayList<Developer>) {
        rv_developer.apply {
            layoutManager = GridLayoutManager(view?.context, 2)
            adapter = GridDeveloperAdapter(list, object : GridDeveloperAdapter.OnItemClickCallback {
                override fun onItemClicked(developer: Developer) {
                    showActionClickCallback(developer)
                }
            })
        }
    }

    private fun showRecyclerCard(list: ArrayList<Developer>) {
        rv_developer.apply {
            layoutManager = LinearLayoutManager(view?.context)
            adapter = CardViewDeveloperAdapter(list, object : CardViewDeveloperAdapter.OnItemClickCallback {
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