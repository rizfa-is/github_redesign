package com.istekno.githubredesign.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.istekno.githubredesign.R
import com.istekno.githubredesign.data.Developer
import com.istekno.githubredesign.fragments.DeveloperFragment
import com.istekno.githubredesign.fragments.detailuser.RepositoryFragment
import kotlinx.android.synthetic.main.activity_developer_detail.*

class DeveloperDetailActivity : AppCompatActivity(), View.OnClickListener {
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
        }

        tv_repository_number.setOnClickListener(this)
        tv_follower_number.setOnClickListener(this)
        tv_following_number.setOnClickListener(this)

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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_repository_number -> {
               val intentRepo = Intent(this@DeveloperDetailActivity, DeveloperInfoActivity::class.java)
                startActivity(intentRepo)
            }
        }
    }
}