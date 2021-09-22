package com.anirudh.finboxapp.utils

import android.app.Activity
import android.content.Context
import android.provider.Settings.System.getString

import com.anirudh.finboxapp.R

object PreferenceUtils {

    private const val PREFERENCE_NAME = "finbox_prefs"
    private const val LOCATION_WORKER_CREATED = "location_worker_created"

    fun setRequestCreated(activity: Activity, hasRequestCreated: Boolean) {
        val sharedPref = activity?.getSharedPreferences(
            PREFERENCE_NAME, Context.MODE_PRIVATE
        )
        var editor = sharedPref.edit()
        editor.putBoolean(LOCATION_WORKER_CREATED, hasRequestCreated)
        editor.commit()
    }

    fun hasRequestCreated(activity: Activity): Boolean {
        val sharedPref = activity?.getSharedPreferences(
            PREFERENCE_NAME, Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean(LOCATION_WORKER_CREATED, false)
    }
}