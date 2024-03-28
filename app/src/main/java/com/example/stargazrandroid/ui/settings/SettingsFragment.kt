package com.example.stargazrandroid.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.stargazrandroid.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}


//    addPreferencesFromResource(R.xml.settings);