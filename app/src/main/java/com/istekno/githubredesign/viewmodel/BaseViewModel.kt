package com.istekno.githubredesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.githubredesign.db.BaseAPI
import com.istekno.githubredesign.model.DeveloperList

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