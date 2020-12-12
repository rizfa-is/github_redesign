package com.istekno.githubredesign.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.istekno.githubredesign.R
import com.istekno.githubredesign.adapters.developerdetail.DeveloperDetailViewPagerAdapter
import com.istekno.githubredesign.databases.BaseAPI
import com.istekno.githubredesign.models.DeveloperDetail
import com.istekno.githubredesign.models.DeveloperList
import com.istekno.githubredesign.views.fragments.DeveloperFragment
import com.istekno.githubredesign.helpers.ResponseAPI
import kotlinx.android.synthetic.main.activity_developer_detail.*

class DeveloperDetailActivity : AppCompatActivity() {

    private lateinit var getAPI: BaseAPI
    private lateinit var responseAPI: ResponseAPI
    private val listDeveloperDetail = ArrayList<DeveloperDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_detail)

        initInheritance()
        developerDetailViewBinding()
        onBtnClick()
    }

    private fun initInheritance() {
        getAPI = BaseAPI()
        responseAPI = ResponseAPI()
    }

    private fun developerDetailViewBinding() {
        val developer = intent.getParcelableExtra<DeveloperList>(DeveloperFragment.INTENT_PARCELABLE)
        if (developer != null) {
            Glide.with(this)
                .load(developer.avatar)
                .into(img_developer_detail)
            tv_username_developer_detail.text = developer.username

            responseAPI.showLoadingDeveloperDetail(progressBar_devdetail1, progressBar_devdetail2, progressBar_devdetail3, progressBar_devdetail4, progressBar_devdetail5, true)
            getAPI.getDeveloperDetailData( progressBar_devdetail3,this, developer.username, listDeveloperDetail) {
                if (listDeveloperDetail.isNotEmpty()) {
                    receiveBundleAction()
                    responseAPI.showLoadingDeveloperDetail(progressBar_devdetail1, progressBar_devdetail2, progressBar_devdetail3, progressBar_devdetail4, progressBar_devdetail5, false)
                }
            }

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