package com.spogss.wibb

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            // show developer options in dev mode
            findPreference<PreferenceCategory>("dev_mode_prefs")?.let { devModePrefs ->
                val isDevMode: Int = Settings.Global.getInt(
                    context?.applicationContext?.contentResolver,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
                )

                devModePrefs.isVisible = isDevMode == 1
            }

            // trigger setDefaultNightMode when the preference is updated
            val themePreference: ListPreference? = findPreference("pref_theme")
            themePreference?.let {
                it.onPreferenceChangeListener =
                    Preference.OnPreferenceChangeListener { _: Preference?,
                                                            newValue: Any ->
                        val nightMode = Integer.parseInt(newValue as String)
                        setDefaultNightMode(nightMode)
                        true
                    }
            }
        }
    }
}