package com.istekno.githubredesign.views.activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.istekno.githubredesign.R
import com.istekno.githubredesign.adapters.developerdetail.DeveloperDetailViewPagerAdapter
import com.istekno.githubredesign.databinding.ActivityDeveloperDetailBinding
import com.istekno.githubredesign.db.DatabaseContract
import com.istekno.githubredesign.db.GithubHelper
import com.istekno.githubredesign.entity.Favorite
import com.istekno.githubredesign.helpers.MappingHelper
import com.istekno.githubredesign.utilities.BaseAPI
import com.istekno.githubredesign.helpers.ResponseAPI
import com.istekno.githubredesign.models.DeveloperDetail
import com.istekno.githubredesign.views.fragments.DeveloperFragment
import kotlinx.android.synthetic.main.activity_developer_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DeveloperDetailActivity : AppCompatActivity() {

    private lateinit var getAPI: BaseAPI
    private lateinit var responseAPI: ResponseAPI
    private lateinit var binding: ActivityDeveloperDetailBinding
    private lateinit var githubHelper: GithubHelper
    private lateinit var favorite: Favorite
    private var listFav = ArrayList<Favorite>()

    private val listDeveloperDetail = ArrayList<DeveloperDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeveloperDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        githubHelper = GithubHelper(applicationContext)
        githubHelper.open()

        loadGithubAsync()

        initInheritance()
        developerDetailViewBinding()
    }

    private fun initInheritance() {
        getAPI = BaseAPI()
        responseAPI = ResponseAPI()
    }

    private fun developerDetailViewBinding() {
        val developer = intent.getParcelableExtra<DeveloperDetail>(DeveloperFragment.INTENT_PARCELABLE)
        favorite = Favorite()
        var username = ""
        var avatar = ""

        if (developer != null) {
            username = developer.username
            avatar = developer.avatar
        } else {
            favorite = intent.getParcelableExtra(DeveloperFragment.FAV_INTENT_PARCELABLE)!!
            username = favorite.username.toString()
            avatar = favorite.avatar.toString()
        }

        Glide.with(this)
            .load(avatar)
            .into(img_developer_detail)
        tv_username_developer_detail.text = username

        responseAPI.showLoadingDeveloperDetail(progressBar_devdetail1, progressBar_devdetail2, progressBar_devdetail3, progressBar_devdetail4, progressBar_devdetail5, true)
        getAPI.getDeveloperDetailData( progressBar_devdetail3,this, username, listDeveloperDetail) {
            if (listDeveloperDetail.isNotEmpty()) {
                receiveBundleAction()
                responseAPI.showLoadingDeveloperDetail(progressBar_devdetail1, progressBar_devdetail2, progressBar_devdetail3, progressBar_devdetail4, progressBar_devdetail5, false)
                checkFavorite(username, avatar)
            }
        }

        val developerDetailAdapter = DeveloperDetailViewPagerAdapter(this, supportFragmentManager, username, avatar)
        vp_developer_detail.adapter = developerDetailAdapter
        tl_developer_detail.setupWithViewPager(vp_developer_detail)

        onBtnClick(username, avatar)
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

    private fun checkFavorite(username: String, avatar: String) {

        for (i in 0 until listFav.size) {
            val list = listFav[i]
            if (list.username == username && list.avatar == avatar) {
                favorite.id = list.id
                developer_detail_btn_favorite.setImageResource(R.drawable.ic_favorite_checked)
                developer_detail_btn_favorite.isSelected = true
            }
        }
    }

    private fun onBtnClick(username: String, avatar: String) {

        topAppBar_detail.setNavigationOnClickListener {
            onBackPressed()
        }

        developer_detail_btn_favorite.setOnClickListener {
            val values = ContentValues()
            values.put(DatabaseContract.FavoriteColums.USERNAME, username)
            values.put(DatabaseContract.FavoriteColums.AVATAR, avatar)

            if (!it.isSelected) {
                val resultAdd = githubHelper.insert(values)

                if (resultAdd > 0) {
                    Toast.makeText(this, "Success add to favorite!", Toast.LENGTH_SHORT).show()

                    developer_detail_btn_favorite.setImageResource(R.drawable.ic_favorite_checked)
                    developer_detail_btn_favorite.isSelected = true
                } else {
                    Toast.makeText(this, "Fail add to favorite!", Toast.LENGTH_SHORT).show()
                }

            } else {
                val resultDelete = githubHelper.deleteById(favorite.id.toString()).toLong()

                if (resultDelete > 0) {
                    Toast.makeText(this, "Success remove from favorite!", Toast.LENGTH_SHORT).show()

                    developer_detail_btn_favorite.setImageResource(R.drawable.ic_favorite_unchecked)
                    developer_detail_btn_favorite.isSelected = false
                } else {
                    Toast.makeText(this, "Fail remove from favorite!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadGithubAsync() {
        val cursor = githubHelper.queryAll()
        val list = MappingHelper.mapCursorToArrayList(cursor)

        if (list.size > 0) {
            listFav = list
        } else {
            Toast.makeText(this@DeveloperDetailActivity, "There's no data!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        githubHelper.close()
    }
}