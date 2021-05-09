package com.spogss.wibb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.spogss.wibb.connection.WibbConnection
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.tools.URLUnifier
import com.spogss.wibb.tools.err.ErrorHandler
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.net.ssl.SSLPeerUnverifiedException


class SplashActivity : AppCompatActivity() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        runOnUiThread {
            val message = when (exception) {
                is SSLPeerUnverifiedException -> "SSL Error. Check certificates and backend logs."
                else -> "Error while loading information."
            }

            exception.printStackTrace()

            Log.e("INITERR", "$message ${exception.message}")
            ErrorHandler.of(this).handle(getString(R.string.error_connection, message))

            startActivity(Intent(applicationContext, SettingsActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()

        // read prefs
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val apiUrl = prefs.getString(getString(R.string.key_pref_api_url), "https://www.wibb.at")
        val nightMode = prefs.getString(getString(R.string.key_pref_theme), "-1")

        // setup conn
        URLUnifier.initialize(apiUrl!!, "mini")
        WibbConnection.initialize(applicationContext)

        // set theme
        nightMode?.let {
            AppCompatDelegate.setDefaultNightMode(Integer.parseInt(it))
        }

        // load static data
        val job = GlobalScope.launch(coroutineExceptionHandler) {
            val beers = WibbConnection.getBeers()
            WibbController.setBeers(beers)
            runOnUiThread { progress_icon.setProgress(33, 100) }

            val stores = WibbConnection.getStores()
            WibbController.setStores(stores)
            runOnUiThread { progress_icon.setProgress(33, 66, 100) }

            val offers = WibbConnection.getOffers()
            WibbController.setOffers(offers)
            runOnUiThread { progress_icon.setProgress(66, 99, 100) }
        }

        GlobalScope.launch(coroutineExceptionHandler) {
            job.join()

            WibbController.setFavourites((WibbController.beers + WibbController.stores).filter {
                prefs.getBoolean(it.text, true)
            })

            runOnUiThread { progress_icon.setProgress(99, 100, 100) }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
}
