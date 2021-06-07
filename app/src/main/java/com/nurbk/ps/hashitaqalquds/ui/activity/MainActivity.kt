package com.nurbk.ps.hashitaqalquds.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import cn.jzvd.JZVideoPlayer
import com.google.android.material.snackbar.Snackbar
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.ActivityMainBinding
import com.nurbk.ps.hashitaqalquds.other.OnShowSnack
import com.nurbk.ps.hashitaqalquds.util.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnShowSnack {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navHostFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shared = PreferencesManager(this)

        setLocale("ar")
        if (shared.isDark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL;
//        setNightMode( false)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentNavHostHome)!!

        val navController = navHostFragment.findNavController()

        NavigationUI.setupWithNavController(
            mBinding.bottomNavigation,
            navController
        )
        var id = 0
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profileFragment -> {
                    if (shared.isLogin) {
                        if (item.itemId != id)
                            navController.navigate(R.id.profileFragment, null, getNavOptions())
                        id = item.itemId

                    } else {

                    }
                }
                R.id.homeFragment -> {
                    if (item.itemId != id) {
                        navController.navigate(R.id.homeFragment, null, getNavOptions())
                        id = item.itemId
                    }
                }
                R.id.mapFragment -> {

                    if (item.itemId != id) {
                        navController.navigate(R.id.mapFragment, null, getNavOptions())
                        id = item.itemId
                    }
                }


            }



            true
        }



        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _: NavController?, destination: NavDestination, arguments: Bundle? ->
                when (destination.id) {
                    R.id.splashFragment -> {
                        window.apply {
                            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                    }
                    R.id.homeFragment, R.id.mapFragment, R.id.profileFragment, R.id.landmarksFragment -> {
                        window.apply {
                            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        mBinding.bottomNavigation.visibility = View.VISIBLE
                    }
                    else -> {
                        window.apply {
                            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        mBinding.bottomNavigation.visibility = View.GONE
                    }
                }
            }

    }

    private fun getNavOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        JZVideoPlayer.releaseAllVideos()
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }

    private fun goToSignIn() {
        Snackbar.make(
            mBinding.root,
            getString(R.string.txtSignIn),
            Snackbar.LENGTH_LONG
        )
            .setAction(getString(R.string.txtSignIn)) {
                navHostFragment.findNavController()
                    .navigate(R.id.action_welcomeFragment_to_signInFragment)
            }.show()
    }

    override fun onShowSnack() {
        goToSignIn()
    }

    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
    }
}