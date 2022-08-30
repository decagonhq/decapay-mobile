package com.decagonhq.decapay.common.data.sharedpreference

interface Preferences {

    fun putToken(token: String)

    fun getToken(): String

    fun putUserEmail(email: String)

    fun getUserEmail(): String

    fun putUserPassword(password: String)

    fun getUserPassword(): String

    fun deleteToken()

    fun putBudgetStartDate(startDate: Long)

    fun getBudgetStartDate(): Long

    fun putBudgetEndDate(endDate: Long)

    fun getBudgetEndDate(): Long

    fun putSelectedDate(date: String)

    fun getSelectedDate(): String
}
