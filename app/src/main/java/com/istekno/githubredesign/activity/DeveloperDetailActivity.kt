package com.istekno.githubredesign.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.istekno.githubredesign.R
import com.istekno.githubredesign.adapter.developerdetail.DeveloperDetailViewPagerAdapter
import com.istekno.githubredesign.api.API
import com.istekno.githubredesign.data.DeveloperDetail
import com.istekno.githubredesign.data.DeveloperList
import com.istekno.githubredesign.fragments.DeveloperFragment
import kotlinx.android.synthetic.main.activity_developer_detail.*

class DeveloperDetailActivity : AppCompatActivity() {

    private val getAPI = API()
    private val listDeveloperDetail = ArrayList<DeveloperDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_detail)

        developerDetailViewBinding()
        onBtnClick()
    }

    private fun developerDetailViewBinding() {
        val developer = intent.getParcelableExtra<DeveloperList>(DeveloperFragment.INTENT_PARCELABLE)
        if (developer != null) {
            Glide.with(this)
                .load(developer.avatar)
                .into(img_developer_detail)
            tv_username_developer_detail.text = developer.username

            getAPI.getDeveloperDetailData(this, developer.username, listDeveloperDetail) { receiveBundleAction() }

            val developerDetailAdapter = DeveloperDetailViewPagerAdapter(this, supportFragmentManager, developer.username, developer.avatar)
            vp_developer_detail.adapter = developerDetailAdapter
            tl_developer_detail.setupWithViewPager(vp_developer_detail)
        }
    }

    private fun receiveBundleAction() {
        val developer = listDeveloperDetail[0]

        tv_name_developer_detail.text = developer.name
        tv_company_developer_detail.text = developer.company
        tv_location_developer_detail.text = developer.location
        tv_repository_number.text = developer.repository.toString()
        tv_follower_number.text = developer.follower.toString()
        tv_following_number.text = developer.following.toString()
    }

    private fun onBtnClick() {

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