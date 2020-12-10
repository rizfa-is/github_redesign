package com.istekno.githubredesign.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.istekno.githubredesign.R
import com.istekno.githubredesign.adapter.developerdetail.DeveloperDetailViewPagerAdapter
import com.istekno.githubredesign.data.Developer
import com.istekno.githubredesign.fragments.DeveloperFragment
import kotlinx.android.synthetic.main.activity_developer_detail.*

class DeveloperDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_detail)

        val developer = intent.getParcelableExtra<Developer>(DeveloperFragment.INTENT_PARCELABLE)
        if (developer != null) {
            Glide.with(this)
                .load(developer.avatar)
                .into(img_developer_detail)

            tv_name_developer_detail.text = developer.name
            tv_company_developer_detail.text = developer.company
            tv_location_developer_detail.text = developer.location
            tv_username_developer_detail.text = developer.username

            tv_repository_number.text = developer.repository.toString()
            tv_follower_number.text = developer.follower.toString()
            tv_following_number.text = developer.following.toString()


            val developerDetailAdapter = DeveloperDetailViewPagerAdapter(this, supportFragmentManager, developer.username, developer.avatar)
            vp_developer_detail.adapter = developerDetailAdapter
            tl_developer_detail.setupWithViewPager(vp_developer_detail)
        }

        topAppBar_detail.setNavigationOnClickListener {
            onBackPressed()
        }

        developer_detail_btn_favorite.setOnClickListener {
           if (!it.isSelected) {
               developer_detail_btn_favorite.setImageResource(R.drawable.ic_favorite_checked)
               developer_detail_btn_favorite.isSelected = true
           } else {
               developer_detail_btn_favorite.setImageResource(R.drawable.ic_favorite_unchecked)
               developer_detail_btn_favorite.isSelected = false
           }
        }
    }
}