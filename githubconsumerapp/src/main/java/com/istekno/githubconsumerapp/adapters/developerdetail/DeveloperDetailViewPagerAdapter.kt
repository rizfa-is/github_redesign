package com.istekno.githubconsumerapp.adapters.developerdetail

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.istekno.githubconsumerapp.R
import com.istekno.githubconsumerapp.views.fragments.detailuser.FollowersFragment
import com.istekno.githubconsumerapp.views.fragments.detailuser.FollowingFragment
import com.istekno.githubconsumerapp.views.fragments.detailuser.RepositoryFragment

class DeveloperDetailViewPagerAdapter(private val mContext: Context, fragmentManager: FragmentManager, private val username: String, private val avatar: String) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.repository, R.string.follower, R.string.following)

    override fun getCount(): Int = tabTitles.size

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null
        when (position) {
            0 -> fragment  = RepositoryFragment(username, avatar)
            1 -> fragment = FollowersFragment(username)
            2 -> fragment = FollowingFragment(username)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return mContext.resources.getString(tabTitles[position])
    }
}