package com.decagonhq.decapay.common.data.sharedpreference

interface Preferences {

    fun putToken(token: String)

    fun getToken(): String
}
