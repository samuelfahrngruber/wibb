package com.spogss.wibb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.spogss.wibb.connection.WibbConnection
import androidx.preference.PreferenceManager
import com.spogss.wibb.tools.URLUnifier
import com.spogss.wibb.tools.err.ErrorHandler
import kotlinx.android.synthetic.main.activity_splash.*
import java.lang.Exception


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
        try {
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            val apiurl = prefs.getString("apiurl", "https://wibb.host")
            URLUnifier.initialize(apiurl!!)

            WibbConnection.initialize(this.applicationContext)

            WibbConnection.loadBeers {
                if (it) {
                    progress_icon.setProgress(50, 100)
                    WibbConnection.loadStores {
                        if (it) {
                            progress_icon.setProgress(50, 100, 100)
                            WibbConnection.loadOffers {
                                if (it) {
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else handleStartupError()
                            }
                        } else handleStartupError()
                    }
                } else handleStartupError()
            }
        }
        catch(ex: Exception){
            ErrorHandler.of(this).handle(ex)
        }
    }

    private fun handleStartupError(){
        Log.e("INITERR", "Error while loading information")

        ErrorHandler.of(this).handle(getString(R.string.toast_connectionError))

        val intent =  Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}
