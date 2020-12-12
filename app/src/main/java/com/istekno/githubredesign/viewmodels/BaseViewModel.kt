package com.istekno.githubredesign.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.githubredesign.databases.BaseAPI
import com.istekno.githubredesign.models.DeveloperList

class BaseViewModel: ViewModel() {

    private lateinit var getBaseAPI: BaseAPI

    val listDeveloper = MutableLiveData<ArrayList<DeveloperList>>()

    fun setListDeveloper(word: String, empty: Boolean, url: String, isMaxActive: Boolean, listAction:() -> Unit) {
        getBaseAPI = BaseAPI()
        getBaseAPI.getDeveloperListData(listDeveloper, empty, url, isMaxActive) { listAction() }
    }

    fun getListDeveloper() : LiveData<ArrayList<DeveloperList>> {
        return listDeveloper
    }
}