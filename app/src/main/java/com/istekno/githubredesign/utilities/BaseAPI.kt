package com.istekno.githubredesign.utilities

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.istekno.githubredesign.models.DeveloperDetail
import com.istekno.githubredesign.models.Follows
import com.istekno.githubredesign.models.Repository
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class BaseAPI {

    fun getDeveloperListData(listDeveloper: MutableLiveData<ArrayList<DeveloperDetail>>, empty: Boolean, url: String, isMaxActive: Boolean) {
        val listItems = ArrayList<DeveloperDetail>()
        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {

                val res = String(responseBody!!)
                val result = if (empty) {
                    JSONArray(res)
                } else {
                    JSONObject(res).getJSONArray("items")
                }

                try {
                    for (i in 0 until result.length()) {
                        val jsonObject = result.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val avatar = jsonObject.getString("avatar_url")

                        val developerDetail = DeveloperDetail()
                        developerDetail.username = username
                        developerDetail.avatar = avatar

                        if (isMaxActive) {
                            if (listItems.size < 5) listItems.add(developerDetail)
                        } else {
                            listItems.add(developerDetail)
                        }
                    }

                    listDeveloper.postValue(listItems)
                    Log.d("BaseAPI() : ", listItems.toString())
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
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                if (error != null) {
                    Log.d("onFailure", errorMessage)
                }
            }
        })
    }

    fun getDeveloperDetailData(loginID: String, arrayList: ArrayList<DeveloperDetail>) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$loginID"
        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = String(responseBody!!)
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

                    val developer = DeveloperDetail()
                    developer.avatar = avatar
                    developer.name = name
                    developer.company = company
                    developer.location = location
                    developer.username = username
                    developer.repository = repository.toInt()
                    developer.follower = follower.toInt()
                    developer.following = following.toInt()

                    arrayList.add(developer)
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
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                if (error != null) {
                    Log.d("onFailure", errorMessage)
                }
            }
        })
    }

    fun getDeveloperDetailData(progressBarID: View, context: Context, loginID: String, arrayList: ArrayList<DeveloperDetail>, listAction: () -> Unit) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$loginID"
        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = String(responseBody!!)
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

                    val developer = DeveloperDetail()
                    developer.avatar = avatar
                    developer.name = name
                    developer.company = company
                    developer.location = location
                    developer.username = username
                    developer.repository = repository.toInt()
                    developer.follower = follower.toInt()
                    developer.following = following.toInt()

                    arrayList.add(developer)
                    listAction()
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

                progressBarID.visibility = View.INVISIBLE

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

    fun getFollowsDetailData(view: View, progressBarID: View, urlMain: String, arrayList: ArrayList<Follows>, listAction: () -> Unit) {

        progressBarID.visibility = View.VISIBLE

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(urlMain, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                progressBarID.visibility = View.INVISIBLE

                val result = responseBody?.let { String(it) }
                try {
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val name = jsonArray.getJSONObject(i).getString("login")
                        val avatar = jsonArray.getJSONObject(i).getString("avatar_url")
                        
                        val repo = Follows()
                        repo.name = name
                        repo.avatar = avatar

                        arrayList.add(repo)
                    }

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                listAction()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                progressBarID.visibility = View.INVISIBLE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                Toast.makeText(view.context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getRepositoryDetailData(view: View, progressBarID: View, urlMain: String, arrayList: ArrayList<Repository>, loginID: String, avatar: String, listAction: () -> Unit) {

        progressBarID.visibility = View.VISIBLE

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 7a0c2e4541faeb65b97b48e93a9881c3f8409fac")
        client.addHeader("User-Agent", "request")
        client.get(urlMain, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                progressBarID.visibility = View.INVISIBLE

                val result = responseBody?.let { String(it) }
                try {
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val name = jsonArray.getJSONObject(i).getString("name")

                        val repo = Repository()
                        repo.name = name
                        repo.username = loginID
                        repo.avatar = avatar

                        arrayList.add(repo)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                listAction()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                progressBarID.visibility = View.INVISIBLE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                Toast.makeText(view.context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}