package com.decagonhq.decapay.feature.usersettings.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.feature.usersettings.data.network.model.Currency

class CurrencyAdaapterItem(
    private val context: Context,
    private val currencies: List<Currency>
) : BaseAdapter(){
    override fun getCount(): Int {
        return currencies.size
    }

    override fun getItem(position: Int): Any {
        return currencies[position]
    }

    override fun getItemId(position: Int): Long {
        return currencies[position].id?.toLong() ?: 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val inflateView = LayoutInflater.from(context).inflate(R.layout.user_settings_currency_item, null, false)
        val displayView = inflateView.findViewById<TextView>(R.id.userSettings_currency_custom_spinner_tv)
        displayView.text = currencies[position].name
        return inflateView
    }
}