package com.decagonhq.decapay.feature.createbudgetlineitems.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.decagonhq.decapay.R
import com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model.CategoryItem

class CategoryItemSpinnerAdapter(private val mContext: Context, private val categoryItems: List<CategoryItem>) : BaseAdapter() {
    override fun getCount(): Int {
        return categoryItems.size
    }

    override fun getItem(position: Int): Any {
        return categoryItems[position]
    }

    override fun getItemId(position: Int): Long {
        return categoryItems.get(position).id?.toLong()!!
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val inflateView = LayoutInflater.from(mContext).inflate(R.layout.create_editline_item_custom_spinner, null, false)
        val displaySpinnerView = inflateView.findViewById<TextView>(R.id.createditlineitem_custom_spinner_tv)
        displaySpinnerView.text = categoryItems.get(position).title
        return inflateView
    }
}
