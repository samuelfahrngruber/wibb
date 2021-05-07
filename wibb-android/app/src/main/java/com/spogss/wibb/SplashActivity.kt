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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()

        var intent = Intent(this, MainActivity::class.java)

        try {
            setup()
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e("INITERR", "Error while loading information. " + e.message)
            ErrorHandler.of(this).handle(getString(R.string.toast_connectionError))

            intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Loads all required data and prepares the app.
     */
    private fun setup() {
        GlobalScope.launch {
            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val apiUrl = prefs.getString("pref_api_url", "https://wibb.host")
            val nightMode = prefs.getString("pref_theme", "-1")

            loadStaticData(apiUrl)
            applyTheme(nightMode)
            FavouriteFilter.readFrom(prefs)
        }
    }

    /**
     * Applies theme from a given night-mode.
     */
    private fun applyTheme(nightMode: String?) {
        nightMode?.let {
            AppCompatDelegate.setDefaultNightMode(Integer.parseInt(it))
        }
    }

    /**
     * Loads all required static data from the backend.
     */
    private suspend fun loadStaticData(apiUrl: String?) = withContext(Dispatchers.Default) {
        URLUnifier.initialize(apiUrl!!, "mini")
        WibbConnection.initialize(applicationContext)

        WibbController.setBeers(WibbConnection.getBeers())
        runOnUiThread { progress_icon.setProgress(33, 100) }

        WibbController.setStores(WibbConnection.getStores())
        runOnUiThread { progress_icon.setProgress(33, 66, 100) }

        WibbController.setOffers(WibbConnection.getOffers())
        runOnUiThread { progress_icon.setProgress(66, 100, 100) }
    }
}
