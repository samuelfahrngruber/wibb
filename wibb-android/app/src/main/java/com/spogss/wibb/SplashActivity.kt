package com.spogss.wibb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.spogss.wibb.connection.WibbConnection
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.tools.FavouriteFilter
import com.spogss.wibb.tools.URLUnifier
import com.spogss.wibb.tools.err.ErrorHandler
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SplashActivity : AppCompatActivity() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("INITERR", "Error while loading information. " + exception.message)
        ErrorHandler.of(this).handle(getString(R.string.toast_connectionError))

        startActivity(Intent(applicationContext, SettingsActivity::class.java))
        finish()
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
        val apiUrl = prefs.getString("pref_api_url", "https://www.wibb.at")
        val nightMode = prefs.getString("pref_theme", "-1")
        FavouriteFilter.readFrom(prefs)

        // setup conn
        URLUnifier.initialize(apiUrl!!, "mini")
        WibbConnection.initialize(applicationContext)

        // set theme
        nightMode?.let {
            AppCompatDelegate.setDefaultNightMode(Integer.parseInt(it))
        }

        // load static data
        val job = GlobalScope.launch(coroutineExceptionHandler) {
            WibbController.setBeers(WibbConnection.getBeers())
            runOnUiThread { progress_icon.setProgress(33, 100) }

            WibbController.setStores(WibbConnection.getStores())
            runOnUiThread { progress_icon.setProgress(33, 66, 100) }

            WibbController.setOffers(WibbConnection.getOffers())
            runOnUiThread { progress_icon.setProgress(66, 100, 100) }
        }

        GlobalScope.launch(coroutineExceptionHandler) {
            job.join()
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
}
