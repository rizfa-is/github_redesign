package com.istekno.githubredesign.views.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.google.android.material.navigation.NavigationView
import com.istekno.githubredesign.R
import com.istekno.githubredesign.alarm.AlarmReceiver

class SettingFragment(
    private val navigationView: NavigationView,
    private val actionBar: androidx.appcompat.widget.Toolbar
) : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER: String
    private lateinit var reminderPreference: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)

        actionBar.menu?.findItem(R.id.act_listOption)?.isVisible = false
        actionBar.menu?.findItem(R.id.act_favorite)?.isVisible = false
        actionBar.title = resources.getString(R.string.setting)
        navigationView.setCheckedItem(R.id.setting_nav_drawer2)

        alarmReceiver = AlarmReceiver()
        init()
        setSummaries()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun init() {
        REMINDER = resources.getString(R.string.key_reminder)
        reminderPreference = findPreference<SwitchPreference>(REMINDER) as SwitchPreference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        reminderPreference.summary = sh.getBoolean(REMINDER, false).toString()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == REMINDER) {
            reminderPreference.isChecked = sharedPreferences.getBoolean(REMINDER, false)
            Toast.makeText(context, "Status: ${reminderPreference.isChecked}", Toast.LENGTH_LONG).show()
            reminderPreference.summary = reminderPreference.isChecked.toString()

            if (reminderPreference.isChecked) {
                context?.let { alarmReceiver.setRepeatingAlarm(it, "09:00", "Let's explore borderless world!") }
            } else {
                context?.let { alarmReceiver.cancelAlarm(it) }
            }
        }
    }
}