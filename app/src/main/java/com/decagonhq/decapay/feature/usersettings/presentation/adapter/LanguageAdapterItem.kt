package com.decagonhq.decapay.feature.usersettings.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.feature.usersettings.data.network.model.Language

class LanguageAdapterItem(
    private val context: Context,
    private val languages: List<Language>
) : BaseAdapter(){
    override fun getCount(): Int {
        return languages.size
    }

    override fun getItem(position: Int): Any {
        return languages[position]
    }

    override fun getItemId(position: Int): Long {
        return languages[position].id?.toLong() ?: 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val inflateView = LayoutInflater.from(context).inflate(R.layout.user_settings_language_item, null, false)
        val displayView = inflateView.findViewById<TextView>(R.id.userSettings_language_custom_spinner_tv)
        displayView.text = languages[position].name
        return inflateView
    }

}