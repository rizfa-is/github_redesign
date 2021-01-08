package com.istekno.githubconsumerapp.views.fragments.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.istekno.githubconsumerapp.R
import com.istekno.githubconsumerapp.adapters.developerdetail.ListFollowingAdapter
import com.istekno.githubconsumerapp.utilities.BaseAPI
import com.istekno.githubconsumerapp.models.Follows
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment(private val username: String) : Fragment() {
    
    private val listFollowing = ArrayList<Follows>()
    private var getAPI = BaseAPI()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val URL = "https://api.github.com/users/$username/following"

        getAPI.getFollowsDetailData(view, progressBar_following_list, URL, listFollowing) { showRecycleList(listFollowing, view) }
    }

    private fun showRecycleList(listFollower: ArrayList<Follows>, view: View) {
        rv_following.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListFollowingAdapter(listFollower)
        }
    }
}