package com.istekno.githubconsumerapp.views.activity

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.istekno.githubconsumerapp.R
import com.istekno.githubconsumerapp.adapters.developerdetail.DeveloperDetailViewPagerAdapter
import com.istekno.githubconsumerapp.databinding.ActivityDeveloperDetailBinding
import com.istekno.githubconsumerapp.db.DatabaseContract
import com.istekno.githubconsumerapp.db.DatabaseContract.FavoriteColumn.Companion.CONTENT_URI
import com.istekno.githubconsumerapp.entity.Favorite
import com.istekno.githubconsumerapp.helpers.MappingHelper
import com.istekno.githubconsumerapp.helpers.ResponseAPI
import com.istekno.githubconsumerapp.models.DeveloperDetail
import com.istekno.githubconsumerapp.utilities.BaseAPI
import com.istekno.githubconsumerapp.views.fragments.DeveloperFragment
import kotlinx.android.synthetic.main.activity_developer_detail.*

class DeveloperDetailActivity : AppCompatActivity() {

    private lateinit var getAPI: BaseAPI
    private lateinit var responseAPI: ResponseAPI
    private lateinit var binding: ActivityDeveloperDetailBinding
    private lateinit var favorite: Favorite
    private lateinit var uriWithId: Uri

    private var listFav = ArrayList<Favorite>()
    private val listDeveloperDetail = ArrayList<DeveloperDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeveloperDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favorite = Favorite()
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
        var username = ""
        var avatar = ""

        if (developer != null) {
            username = developer.username
            avatar = developer.avatar
        } else {
            favorite = intent.getParcelableExtra(DeveloperFragment.FAV_INTENT_PARCELABLE)!!
            username = favorite.username.toString()
            avatar = favorite.avatar.toString()
            uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + favorite.id)
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
            values.put(DatabaseContract.FavoriteColumn.USERNAME, username)
            values.put(DatabaseContract.FavoriteColumn.AVATAR, avatar)

            if (!it.isSelected) {
                contentResolver.insert(CONTENT_URI, values)

                Toast.makeText(this, "Success add to favorite!", Toast.LENGTH_SHORT).show()

                developer_detail_btn_favorite.setImageResource(R.drawable.ic_favorite_checked)
                developer_detail_btn_favorite.isSelected = true
            } else {
                contentResolver.delete(uriWithId, null, null)

                Toast.makeText(this, "Success remove from favorite!", Toast.LENGTH_SHORT).show()

                developer_detail_btn_favorite.setImageResource(R.drawable.ic_favorite_unchecked)
                developer_detail_btn_favorite.isSelected = false
            }
        }
    }

    private fun loadGithubAsync() {
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        val list = MappingHelper.mapCursorToArrayList(cursor)

        if (list.size > 0) {
            listFav = list
        } else {
            Toast.makeText(this@DeveloperDetailActivity, "There's no data!", Toast.LENGTH_SHORT).show()
        }
    }
}