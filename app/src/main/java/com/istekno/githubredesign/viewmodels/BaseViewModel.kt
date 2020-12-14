package com.istekno.githubredesign.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.githubredesign.databases.BaseAPI
import com.istekno.githubredesign.models.DeveloperList

class BaseViewModel: ViewModel() {

    private lateinit var getBaseAPI: BaseAPI

    val listDeveloper = MutableLiveData<ArrayList<DeveloperList>>()

    fun setListDeveloper(word: String?, empty: Boolean, isMaxActive: Boolean) {
        val url = "https://api.github.com/search/users?q=\"${word}\""
        getBaseAPI = BaseAPI()
        getBaseAPI.getDeveloperListData(listDeveloper, empty, url, isMaxActive)
        Log.d("setListDeveloper() : ", listDeveloper.toString())
    }

    fun setListDeveloperByPass(empty: Boolean, isMaxActive: Boolean) {
        val url = "https://api.github.com/users"
        getBaseAPI = BaseAPI()
        getBaseAPI.getDeveloperListData(listDeveloper, empty, url, isMaxActive)
    }

    fun getListDeveloper() : LiveData<ArrayList<DeveloperList>> {
        return listDeveloper
    }
}