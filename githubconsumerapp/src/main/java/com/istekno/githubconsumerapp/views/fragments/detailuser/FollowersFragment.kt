package com.istekno.githubconsumerapp.views.fragments.detailuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.istekno.githubconsumerapp.R
import com.istekno.githubconsumerapp.adapters.developerdetail.ListFollowersAdapter
import com.istekno.githubconsumerapp.utilities.BaseAPI
import com.istekno.githubconsumerapp.models.Follows
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment(private val username: String) : Fragment() {

    private val listFollowers = ArrayList<Follows>()
    private val getAPI = BaseAPI()

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

        getAPI.getFollowsDetailData(view, progressBar_followers_list, URL, listFollowers) { showRecycleList(listFollowers) }
    }

    private fun showRecycleList(listFollower: ArrayList<Follows>) {
        rv_followers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListFollowersAdapter(listFollower)
        }
    }
}