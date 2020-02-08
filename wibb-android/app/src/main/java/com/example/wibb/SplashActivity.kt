package com.example.wibb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wibb.connection.WibbConnection
import com.example.wibb.controller.WibbController
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        doSplashStuff()
    }

    private fun handleStartupError(){

    }

    private fun doSplashStuff(){
        WibbConnection.instance.initialize(this.applicationContext)

        WibbConnection.instance.loadBeers {
            if (it) WibbConnection.instance.loadStores {
                if (it) WibbConnection.instance.loadOffers {
                    if (it) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else handleStartupError()
                }
                else handleStartupError()
            }
            else handleStartupError()
        }
    }
}
