package com.decagonhq.decapay.presentation.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.decagonhq.decapay.R
import com.decagonhq.decapay.presentation.MainActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            delay(1000)
            goToApp()
        }
    }

    private fun goToApp() {
        runOnUiThread {

            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}
