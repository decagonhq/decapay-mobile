package com.decagonhq.decapay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.decagonhq.decapay.feature.login.LoginFragment

class MainActivity : AppCompatActivity() {
    /**
     * add views and variables
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val manageFragment = supportFragmentManager
        val transaction = manageFragment.beginTransaction()
        transaction.add(R.id.main_activity_view_login_fragment, LoginFragment())
        transaction.commit()
    }
}
