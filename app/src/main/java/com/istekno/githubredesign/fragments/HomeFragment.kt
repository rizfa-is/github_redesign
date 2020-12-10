package com.istekno.githubredesign.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.activity.DeveloperDetailActivity
import com.istekno.githubredesign.adapter.homefragment.CardViewExploreContentAdapter
import com.istekno.githubredesign.adapter.homefragment.CardViewMostPopularAdapter
import com.istekno.githubredesign.data.Content
import com.istekno.githubredesign.data.Developer
import com.istekno.githubredesign.data.MainData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_developer.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment(private val navigationView : NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    private val listMostPopular = ArrayList<Developer>()
    private val listExploreContent = ArrayList<Content>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu?.findItem(R.id.act_listOption)?.isVisible = false
        actionBar.menu?.findItem(R.id.act_favorite)?.isVisible = true
        actionBar.menu?.findItem(R.id.act_search)?.isVisible = false
        actionBar.title = resources.getString(R.string.home)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.home_nav_drawer)
        val url ="https://api.github.com/users"

        getDeveloperListData(url)

        listExploreContent.addAll(MainData.listDataExploreContent)
        rv_explore_content.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

            val cardAdapter = CardViewExploreContentAdapter(listExploreContent, object : CardViewExploreContentAdapter.OnItemClickCallback {
                override fun onItemClicked(content: Content) {
                    showActionClickCallbackExplore(content)
                }
            })
            adapter = cardAdapter
        }

        actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
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

    private fun showActionClickCallbackPopular(developer: Developer) {
        val intent = Intent(this.context, DeveloperDetailActivity::class.java)
        intent.putExtra(DeveloperFragment.INTENT_PARCELABLE, developer)
        startActivity(intent)
    }

    private fun showActionClickCallbackExplore(content: Content) {
        var fragmentClass = Fragment()
        var fragmentTAG : String? = null

        when(content.name) {
            "Developer" -> {
                fragmentClass = DeveloperFragment(navigationView, actionBar)
                fragmentTAG = DeveloperFragment::class.java.simpleName
            }

            "Challenges" -> {
                fragmentClass = ChallengesFragment(navigationView, actionBar)
                fragmentTAG = ChallengesFragment::class.java.simpleName
            }

            "Study Room" -> {
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

    fun getDeveloperListData(url : String) {

        progressBar_home_list.visibility = View.VISIBLE

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
                progressBar_home_list.visibility = View.INVISIBLE
                val res = responseBody?.let { String(it) }
                val result = JSONArray(res)

                try {
                    for (i in 0 until result.length()) {
                        val jsonObject = result.getJSONObject(i)
                        loginId = jsonObject.getString("login")
                        getDeveloperDetailData(loginId)
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
                progressBar_home_list.visibility = View.INVISIBLE
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

    fun getDeveloperDetailData(loginId: String) {
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

                    if (listMostPopular.size < 5) {
                        listMostPopular.add(developer)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                listMostPopular.sortByDescending { it.follower }
                rv_most_popular.apply {
                    layoutManager = LinearLayoutManager(view?.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = CardViewMostPopularAdapter(listMostPopular, object : CardViewMostPopularAdapter.OnItemClickCallback {
                        override fun onItemClicked(developer: Developer) {
                            showActionClickCallbackPopular(developer)
                        }
                    })
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
}