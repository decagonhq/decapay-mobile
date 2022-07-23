package com.decagonhq.decapay.presentation

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /** SET STATUS BAR COLOR FOR AP1 LEVEL 22 AND BELOW */
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//         setStatusBarColor(this, R.color.black)
//        }

        /** INITIALISE DRAWER MENU LISTENER */
        val navigationView: NavigationView = binding.navView
        navigationView.itemIconTintList = null

        drawerLayout = binding.drawerLayout

        binding.mainActivityHamburgerIb.setOnClickListener {
            drawerLayout.openDrawer(binding.navView)
        }

        selectNavigationItem(navigationView)
    }

    private fun selectNavigationItem(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            navController = Navigation.findNavController(this, R.id.main_activity_fragment_container_fcv)
            when(it.itemId) {
                R.id.menu_dashboard ->  {


                   // navController.navigate(R.id.loginFragment)
                }
                R.id.menu_budget -> {
                    navController.navigate(R.id.testFragment)
                }
                R.id.menu_budget_category -> {
                   // navController.navigate(R.id.loginFragment)
                }R.id.menu_logout -> {
                //navController.p
                    navController.navigate(R.id.loginFragment)
                }
            }

            it.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }




}
