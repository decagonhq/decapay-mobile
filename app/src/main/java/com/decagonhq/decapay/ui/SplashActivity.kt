package com.decagonhq.decapay.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.decagonhq.decapay.MainActivity
import com.decagonhq.decapay.R
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

    private fun goToApp(){
        runOnUiThread{

            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }


        }
    }
}