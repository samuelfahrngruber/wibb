package com.example.wibb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wibb.connection.WibbConnection
import com.example.wibb.controller.WibbController
import kotlinx.android.synthetic.main.activity_splash.*
import android.content.SharedPreferences
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.wibb.tools.URLUnifier


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

    private fun doSplashStuff(){
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val apiurl = prefs.getString("apiurl", "http://localhost:8080")
        URLUnifier.instance.initialize(apiurl!!)

        WibbConnection.instance.initialize(this.applicationContext)

        WibbConnection.instance.loadBeers {
            if (it) {
                //progress_icon.setProgress(33, true)
                WibbConnection.instance.loadStores {
                    if (it){
                        WibbConnection.instance.loadOffers {
                            if (it) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else handleStartupError()
                        }
                    }
                    else handleStartupError()
                }
            }
            else handleStartupError()
        }
    }

    private fun handleStartupError(){
        // todo: fix 204 no content error
        Log.e("INITERR", "Error while loading information")

        Toast.makeText(this, R.string.toast_connectionError, Toast.LENGTH_LONG).show()

        val intent =  Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}
