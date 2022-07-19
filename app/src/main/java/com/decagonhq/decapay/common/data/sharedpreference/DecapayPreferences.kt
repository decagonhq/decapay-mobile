package com.decagonhq.decapay.common.data.sharedpreference

import android.content.Context
import javax.inject.Inject

class DecapayPreferences @Inject constructor(context: Context) : Preferences {

    companion object {
        const val PREFERENCES_NAME = "DECAPAY_PREFERENCES"
    }

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun putToken(token: String) {
        preferences.edit().putString(PreferenceConstants.KEY_TOKEN, token).apply()
    }

    override fun getToken(): String {
        return preferences.getString(PreferenceConstants.KEY_TOKEN, "").orEmpty()
    }
}
