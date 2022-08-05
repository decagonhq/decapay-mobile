package com.decagonhq.decapay.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity() {

    @Inject
    lateinit var preference: Preferences

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var sendDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // pass data to create budget fragment

        if (preference.getToken().isEmpty()) {
            binding.mainActivityHamburgerIb.visibility = View.GONE
            binding.mainActivityDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        /** INITIALISE DRAWER MENU LISTENER */
        val navigationView: NavigationView = binding.mainActivityNavViewNv
        navigationView.itemIconTintList = null

        drawerLayout = binding.mainActivityDrawerLayout

        binding.mainActivityHamburgerIb.setOnClickListener {
            drawerLayout.openDrawer(binding.mainActivityNavViewNv)
        }

        selectNavigationItem(navigationView)
    }

    private fun selectNavigationItem(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            navController =
                Navigation.findNavController(this, R.id.main_activity_fragment_container_fcv)
            when (it.itemId) {
                R.id.menu_dashboard -> {

                    // navController.navigate(R.id.loginFragment)
                }
                R.id.menu_budget -> {
                    // navController.navigate(R.id.testFragment)
                }
                R.id.menu_budget_category -> {
                    // navController.navigate(R.id.loginFragment)
                }
                R.id.menu_logout -> {
                    preference.deleteToken()
                    navController.navigate(R.id.loginFragment)
                    hideDrawer()
                }
            }

            it.isChecked = true
            binding.mainActivityDrawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun hideDrawer() {
        binding.mainActivityHamburgerIb.visibility = View.GONE
        binding.mainActivityDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun revealDrawer() {
        binding.mainActivityHamburgerIb.visibility = View.VISIBLE
        binding.mainActivityDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

//    override fun passDataFromOptionBottomSheetToCreateBudgetFragment(date: String) {
//        Log.d("MAIN", "SEND DATA: $date")
//        passToBundle(date)
//        sendDate = date
//        // pass this data to the create budget fragment
//    }

//    private fun passToBundle(date: String){
//        val bundle = Bundle()
//        bundle.putString("date", date)
//        val createBudgetFragment = CreateBudgetFragment()
//        createBudgetFragment.arguments = bundle
//        supportFragmentManager.beginTransaction().add(createBudgetFragment, "createBudgetFragment").commit()
//    }
}
