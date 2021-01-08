package com.istekno.githubconsumerapp.views.fragments.detailuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.istekno.githubconsumerapp.R
import com.istekno.githubconsumerapp.adapters.developerdetail.ListRepositoryAdapter
import com.istekno.githubconsumerapp.utilities.BaseAPI
import com.istekno.githubconsumerapp.models.Repository
import kotlinx.android.synthetic.main.fragment_repository.*

class RepositoryFragment(private val username: String, private val avatar: String) : Fragment() {

    private val listRepository = ArrayList<Repository>()
    private val getAPI = BaseAPI()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val URL = "https://api.github.com/users/$username/repos"

        getAPI.getRepositoryDetailData(view, progressBar_repo_list, URL, listRepository, username, avatar) { showRecycleList(listRepository) }
    }

    private fun showRecycleList(listRepo: ArrayList<Repository>) {
        rv_repository.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListRepositoryAdapter(listRepo)
        }
    }

}