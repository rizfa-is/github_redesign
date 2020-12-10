package com.istekno.githubredesign.fragments.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.istekno.githubredesign.R
import com.istekno.githubredesign.adapter.developerdetail.ListFollowersAdapter
import com.istekno.githubredesign.data.Follows
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.android.synthetic.main.fragment_repository.*
import org.json.JSONArray
import java.lang.Exception

class FollowersFragment(private val username: String?) : Fragment() {

    private val listFollowers = ArrayList<Follows>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val URL = "https://api.github.com/users/$username/followers"

        getFollowersDetailData(URL) { showRecycleList(listFollowers) }
    }
    
    private fun getFollowersDetailData(urlMain: String, listRepo: () -> Unit) {
        progressBar_followers_list.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(urlMain, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                progressBar_followers_list.visibility = View.INVISIBLE

                val result = responseBody?.let { String(it) }
                try {
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        bindingData(jsonArray, i)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                listRepo()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                progressBar_followers_list.visibility = View.INVISIBLE

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

    private fun bindingData(responsArray: JSONArray, position: Int) {
        val name = responsArray.getJSONObject(position).getString("login")
        val avatar = responsArray.getJSONObject(position).getString("avatar_url")

        val repo = Follows()
        repo.name = name
        repo.avatar = avatar

        listFollowers.add(repo)
    }

    private fun showRecycleList(listFollower: ArrayList<Follows>) {
        rv_followers.apply {
            layoutManager = LinearLayoutManager(view?.context)
            adapter = ListFollowersAdapter(listFollower)
        }
    }
}