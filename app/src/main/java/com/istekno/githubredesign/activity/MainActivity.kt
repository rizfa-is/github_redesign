package com.istekno.githubredesign.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectionCheck(this)

        val mFragmentManager = supportFragmentManager
        val mFragment = HomeFragment(nav_view, topAppBar)
        val fragment = mFragmentManager.findFragmentByTag(mFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_layout, mFragment, HomeFragment::class.java.simpleName)
                .commit()
        }

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToggle = ActionBarDrawerToggle(this, drawer_layout, topAppBar,
            R.string.open,
            R.string.close
        )

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mToggle.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_nav_drawer -> {
                fragmentProperties(HomeFragment(nav_view, topAppBar), HomeFragment::class.java.simpleName)
            }

            R.id.developer_nav_drawer -> {
                fragmentProperties(DeveloperFragment(nav_view, topAppBar), DeveloperFragment::class.java.simpleName)
            }

            R.id.challenges_nav_drawer -> {
                fragmentProperties(ChallengesFragment(nav_view, topAppBar), ChallengesFragment::class.java.simpleName)
            }

            R.id.studyRoom_nav_drawer -> {
                fragmentProperties(StudyRoomFragment(nav_view, topAppBar), StudyRoomFragment::class.java.simpleName)
            }

            R.id.favorit_nav_drawer2 -> {
                fragmentProperties(FavoriteFragment(nav_view, topAppBar), FavoriteFragment::class.java.simpleName)
            }

            R.id.profil_nav_drawer2 -> {
                fragmentProperties(ProfilFragment(nav_view, topAppBar), ProfilFragment::class.java.simpleName)
            }

            R.id.change_language_nav_drawer2 -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }

            R.id.aboutUs_nav_drawer2 -> {
                fragmentProperties(AboutUsFragment(nav_view, topAppBar), AboutUsFragment::class.java.simpleName)
                nav_view.setCheckedItem(R.id.aboutUs_nav_drawer2)
            }
        }
        return false
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
        } else super.onBackPressed()
    }

    private fun fragmentProperties(mFragment: Fragment, fragmentTag: String?) {
        val mFragmentManager = supportFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, mFragment, fragmentTag)
            commit()
        }

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
        }
    }

    private fun connectionCheck(context: Context) {
        val connectManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val checkNetwork = connectManager.activeNetworkInfo

        if (checkNetwork == null || !checkNetwork.isConnected || !checkNetwork.isAvailable) {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.apply {
                setTitle("Network Info")
                setMessage("No internet connection\nCheck your internet connectivity and try again")
                setIcon(R.drawable.ic_no_internet)
                setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    onBackPressed()
                })
                create()
                show()
            }
        } else {
          Toast.makeText(context, "Internet connection OK", Toast.LENGTH_LONG).show()
        }
    }
}