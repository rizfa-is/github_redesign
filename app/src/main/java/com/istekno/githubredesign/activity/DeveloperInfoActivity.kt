package com.istekno.githubredesign.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.istekno.githubredesign.R
import com.istekno.githubredesign.fragments.HomeFragment
import com.istekno.githubredesign.fragments.detailuser.RepositoryFragment
import kotlinx.android.synthetic.main.activity_developer_info.*
import kotlinx.android.synthetic.main.activity_main.*

class DeveloperInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_info)

        val mFragmentManager = supportFragmentManager
        val mFragment = RepositoryFragment()
        val fragment = mFragmentManager.findFragmentByTag(mFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            mFragmentManager.beginTransaction().apply {
                add(R.id.frame_layout_developer_info, mFragment, RepositoryFragment::class.java.simpleName)
                commit()
            }
        }

        topAppBar_developer_info.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}