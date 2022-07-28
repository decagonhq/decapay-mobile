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

    override fun putUserEmail(email: String) {
        preferences.edit().putString(PreferenceConstants.KEY_LOGIN_USER_EMAIL, email).apply()
    }

    override fun getUserEmail(): String {
        return preferences.getString(PreferenceConstants.KEY_LOGIN_USER_EMAIL, "").orEmpty()
    }

    override fun putUserPassword(password: String) {
        preferences.edit().putString(PreferenceConstants.KEY_LOGIN_USER_PASSWORD, password).apply()
    }

    override fun getUserPassword(): String {
        return preferences.getString(PreferenceConstants.KEY_LOGIN_USER_PASSWORD, "").orEmpty()
    }
}
