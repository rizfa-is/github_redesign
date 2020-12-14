package com.istekno.githubredesign.helpers

import android.app.Activity
import android.view.View
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_repository.*

class ResponseAPI {

    private val mActivity = Activity()

    fun showLoadingHome(state: Boolean) {
        if (state) {
            mActivity.progressBar_home_list.visibility = View.VISIBLE
        } else {
            mActivity.progressBar_home_list.visibility = View.GONE
        }
    }

    fun showLoadingDeveloperFragment(progressBarID: View, state: Boolean) {
        if (state) {
            progressBarID.visibility = View.VISIBLE
        } else {
            progressBarID.visibility = View.GONE
        }
    }

    fun showLoadingDeveloperDetail(progressBarID1: View, progressBarID2: View, progressBarID3: View, progressBarID4: View, progressBarID5: View, state: Boolean) {
        if (state) {
            progressBarID1.visibility = View.VISIBLE
            progressBarID2.visibility = View.VISIBLE
            progressBarID3.visibility = View.VISIBLE
            progressBarID4.visibility = View.VISIBLE
            progressBarID5.visibility = View.VISIBLE
        } else {
            progressBarID1.visibility = View.GONE
            progressBarID2.visibility = View.GONE
            progressBarID3.visibility = View.GONE
            progressBarID4.visibility = View.GONE
            progressBarID5.visibility = View.GONE
        }
    }

    fun showLoadingRepositoryFragment(state: Boolean) {
        if (state) {
            mActivity.progressBar_repo_list.visibility = View.VISIBLE
        } else {
            mActivity.progressBar_repo_list.visibility = View.GONE
        }
    }

    fun showLoadingFollowersFragment(state: Boolean) {
        if (state) {
            mActivity.progressBar_followers_list.visibility = View.VISIBLE
        } else {
            mActivity.progressBar_followers_list.visibility = View.GONE
        }
    }

    fun showLoadingFollowingFragment(state: Boolean) {
        if (state) {
            mActivity.progressBar_following_list.visibility = View.VISIBLE
        } else {
            mActivity.progressBar_following_list.visibility = View.GONE
        }
    }
}