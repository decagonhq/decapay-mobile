package com.decagonhq.decapay.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.decagonhq.decapay.R
import com.decagonhq.decapay.common.data.sharedpreference.Preferences
import com.decagonhq.decapay.common.utils.resource.Resource
import com.decagonhq.decapay.common.utils.uihelpers.showInfoMsgTokenExpired
import com.decagonhq.decapay.databinding.ActivityMainBinding
import com.decagonhq.decapay.feature.signout.data.network.model.SignOutRequestBody
import com.decagonhq.decapay.feature.signout.presentation.MainActivityViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var preference: Preferences
    private val activityViewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var header:View
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpViewModelListener()
        val email = preference.getUserEmail()
        val name = preference.getUserName()

        if (preference.getToken().isEmpty()) {
            binding.mainActivityHamburgerIb.visibility = View.GONE
            binding.mainActivityDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
        }

        /** INITIALISE DRAWER MENU LISTENER */
        val navigationView: NavigationView = binding.mainActivityNavViewNv
        navigationView.itemIconTintList = null

        drawerLayout = binding.mainActivityDrawerLayout

        binding.mainActivityHamburgerIb.setOnClickListener {
            drawerLayout.openDrawer(binding.mainActivityNavViewNv)
        }
         header = navigationView.getHeaderView(0)
        val emailView = header.findViewById<TextView>(R.id.menu_header_email_tv)
        val nameView = header.findViewById<TextView>(R.id.menu_header_name_tv)

        emailView.text = ""
        nameView.text = ""
       // val logo = header.findViewById<ImageView>(R.id.menu_header_profile_picture_iv)

        header.setOnClickListener{

            navController =
                Navigation.findNavController(this, R.id.main_activity_fragment_container_fcv)
            navController.navigate(R.id.userProfileFragment)
            binding.mainActivityDrawerLayout.closeDrawer(GravityCompat.START)
        }

        selectNavigationItem(navigationView)
    }

    private fun setUpViewModelListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.signOutResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            Snackbar.make(
                                binding.root,
                                it.data.message,
                                Snackbar.LENGTH_LONG
                            ).show()
                            preference.deleteToken()
                        }
                        is Resource.Error -> {
                            // check when it is UNAUTHORIZED
                            when (it.message) {
                                "UNAUTHORIZED" -> {
                                    // navigate to login
                                    // show a dialog
                                    navController.navigate(R.id.loginFragment)
                                    showInfoMsgTokenExpired()
                                }
                                else -> {
                                    // nothing
                                }
                            }
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            }
        }
    }

    private fun selectNavigationItem(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            navController =
                Navigation.findNavController(this, R.id.main_activity_fragment_container_fcv)
            when (it.itemId) {
//                R.id.menu_dashboard -> {
//
//                    navController.navigate(R.id.budgetListFragment)
//                }
                R.id.menu_budget -> {
                    navController.navigate(R.id.budgetListFragment)
                }
                R.id.menu_budget_category -> {
                    navController.navigate(R.id.budgetCategoryList)
                }
                R.id.menu_logout -> {
                    activityViewModel.signOutUser(SignOutRequestBody(preference.getToken()))
                    navController.navigate(R.id.loginFragment)
                    hideDrawer()
                }
            }

            it.isChecked = true
            binding.mainActivityDrawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun hideDrawer() {
        binding.mainActivityHamburgerIb.visibility = View.GONE
        binding.mainActivityDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun revealDrawer() {
        binding.mainActivityHamburgerIb.visibility = View.VISIBLE
        binding.mainActivityDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun navigateToLogIn() {
        navController.navigate(R.id.loginFragment)
    }

    override fun updateName(name: String, email: String) {
        val emailView = header.findViewById<TextView>(R.id.menu_header_email_tv)
        val nameView = header.findViewById<TextView>(R.id.menu_header_name_tv)

        emailView.text = email
        nameView.text = name
    }
}
