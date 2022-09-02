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

    override fun putUserName(name: String) {
        preferences.edit().putString(PreferenceConstants.USER_NAME, name).apply()
    }

    override fun getUserName():String {
        return preferences.getString(PreferenceConstants.USER_NAME, "").orEmpty()
    }

    override fun putUserPassword(password: String) {
        preferences.edit().putString(PreferenceConstants.KEY_LOGIN_USER_PASSWORD, password).apply()
    }

    override fun getUserPassword(): String {
        return preferences.getString(PreferenceConstants.KEY_LOGIN_USER_PASSWORD, "").orEmpty()
    }

    override fun deleteToken() {
        preferences.edit().remove(PreferenceConstants.KEY_TOKEN).apply()
    }

    override fun putBudgetStartDate(startDate: Long) {
        preferences.edit().putLong(PreferenceConstants.BUDGET_START_DATE, startDate).apply()
    }

    override fun getBudgetStartDate(): Long {
        return preferences.getLong(PreferenceConstants.BUDGET_START_DATE, 0)
    }

    override fun putBudgetEndDate(endDate: Long) {
        preferences.edit().putLong(PreferenceConstants.BUDGET_END_DATE, endDate).apply()
    }

    override fun getBudgetEndDate(): Long {
        return preferences.getLong(PreferenceConstants.BUDGET_END_DATE, 0)
    }

    override fun putSelectedDate(date: String) {
        preferences.edit().putString(PreferenceConstants.CALENDAR_SELECTED_DATE, date).apply()
    }

    override fun getSelectedDate(): String {
        return preferences.getString(PreferenceConstants.CALENDAR_SELECTED_DATE, "").orEmpty()
    }

    override fun putExpenseCategoryTitle(categoryTitle: String) {
        preferences.edit().putString(PreferenceConstants.EXPENSE_CATEGORY_TITLE, categoryTitle).apply()
    }

    override fun getExpenseCategoryTitle(): String {
        return preferences.getString(PreferenceConstants.EXPENSE_CATEGORY_TITLE, "").orEmpty()
    }
}
