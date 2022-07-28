package com.decagonhq.decapay.common.data.sharedpreference

interface Preferences {

    fun putToken(token: String)

    fun getToken(): String

    fun putUserEmail(email: String)

    fun getUserEmail(): String

    fun putUserPassword(password: String)

    fun getUserPassword(): String

    fun deleteToken()
}
