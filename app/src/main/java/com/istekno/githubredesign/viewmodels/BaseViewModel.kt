package com.istekno.githubredesign.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.githubredesign.databases.BaseAPI
import com.istekno.githubredesign.models.DeveloperDetail

class BaseViewModel: ViewModel() {

    private lateinit var getBaseAPI: BaseAPI

    val listDeveloper = MutableLiveData<ArrayList<DeveloperDetail>>()

    fun setListDeveloper(word: String?, empty: Boolean, isMaxActive: Boolean) {
        val url = "https://api.github.com/search/users?q=\"${word}\""
        getBaseAPI = BaseAPI()
        getBaseAPI.getDeveloperListData(listDeveloper, empty, url, isMaxActive)
    }

    fun setListDeveloperByPass(empty: Boolean, isMaxActive: Boolean) {
        val url = "https://api.github.com/users"
        getBaseAPI = BaseAPI()
        getBaseAPI.getDeveloperListData(listDeveloper, empty, url, isMaxActive)
    }

    fun getListDeveloper() : LiveData<ArrayList<DeveloperDetail>> {
        return listDeveloper
    }
}