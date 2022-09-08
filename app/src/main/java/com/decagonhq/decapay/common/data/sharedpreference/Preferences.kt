package com.decagonhq.decapay.common.data.sharedpreference

interface Preferences {

    fun putToken(token: String)

    fun getToken(): String

    fun putUserEmail(email: String)

    fun getUserEmail(): String

    fun putUserName(name: String)

    fun getUserName(): String

    fun putUserPassword(password: String)

    fun getUserPassword(): String

    fun deleteToken()

    fun putBudgetStartDate(startDate: Long)

    fun getBudgetStartDate(): Long

    fun putBudgetEndDate(endDate: Long)

    fun getBudgetEndDate(): Long

    fun putSelectedDate(date: String)

    fun getSelectedDate(): String

    fun putExpenseCategoryTitle(categoryTitle: String)

    fun getExpenseCategoryTitle(): String

    fun putCountry(country: String)

    fun getCountry(): String

    fun putCurrency(currency: String)

    fun getCurrency(): String

    fun putLanguage(language: String)

    fun getLanguage(): String
}
