package com.nurbk.ps.hashitaqalquds.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navHostFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL;

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
var id= 0
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profileFragment -> {
                    if (item.itemId != id)
                        navController.navigate(R.id.profileFragment, null, getNavOptions())

                }
                R.id.homeFragment -> {
                    if (item.itemId != id)
                    navController.navigate(R.id.homeFragment, null, getNavOptions())
                }
                R.id.mapFragment -> {
                    if (item.itemId != id)
                    navController.navigate(R.id.mapFragment, null, getNavOptions())
                }

            }
            id=item.itemId
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
                    R.id.homeFragment, R.id.mapFragment, R.id.profileFragment -> {
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

}