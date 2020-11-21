package com.istekno.githubredesign.fragments

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.istekno.githubredesign.data.MainData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_developer.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class DeveloperFragment(private val navigationView: NavigationView, private val actionBar: androidx.appcompat.widget.Toolbar) : Fragment() {

    private var listDeveloper = ArrayList<Developer>()

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
        val TAG = DeveloperFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar.menu.findItem(R.id.act_listOption).isVisible = true
        actionBar.menu?.findItem(R.id.act_favorite)?.isVisible = true
        actionBar.title = "Developer"

        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_developer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setCheckedItem(R.id.developer_nav_drawer)

        getDeveloperListData(0)
//        showRecyclerList()

        actionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fab_list -> {
                    getDeveloperListData(0)
                }
                R.id.fab_grid -> {
                    getDeveloperListData(1)
                }
                R.id.fab_cardView -> {
                    getDeveloperListData(2)
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

    private fun getDeveloperListData(switchNumber: Int) {
        progressBar_developer_list.visibility = View.VISIBLE
        var loginId = ""
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users"
        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                progressBar_developer_list.visibility = View.INVISIBLE

                val result = String(responseBody)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
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
                responseBody: ByteArray,
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

    private fun getDeveloperDetailData(loginId: String, switchNumber: Int) {
        progressBar_developer_list.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/${loginId}"
        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                progressBar_developer_list.visibility = View.INVISIBLE

                val result = String(responseBody)
                try {
                    val responsObject = JSONObject(result)
                    val name = responsObject.getString("login")
                    val location = responsObject.getString("location")
                    val avatar = responsObject.getString("avatar_url")


                    val developer = Developer()
                    developer.username = name
                    developer.location = location
                    developer.avatar = avatar
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
                responseBody: ByteArray,
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