package com.decagonhq.decapay.feature.usersettings.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.feature.usersettings.data.network.model.Country

class CountryAdapterItem(
    private val context: Context,
    private val countries: List<Country>
) : BaseAdapter(){
    override fun getCount(): Int {
        return countries.size
    }

    override fun getItem(position: Int): Any {
        return countries[position]
    }

    override fun getItemId(position: Int): Long {
        return countries[position].id?.toLong() ?: 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val inflateView = LayoutInflater.from(context).inflate(R.layout.user_settings_country_location_item, null, false)
        val displaySpinnerView = inflateView.findViewById<TextView>(R.id.userSettings_country_custom_spinner_tv)
        displaySpinnerView.text = countries[position].name
        return inflateView
    }
}