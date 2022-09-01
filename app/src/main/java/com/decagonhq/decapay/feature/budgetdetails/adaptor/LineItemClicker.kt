package com.decagonhq.decapay.feature.budgetdetails.adaptor

import android.view.View
import com.decagonhq.decapay.feature.budgetdetails.data.network.model.LineItem

interface LineItemClicker {

    fun onClickItemEllipsis(currentLineItem: LineItem, position: Int, view: View)
    fun onClickItemLog(currentLineItem: LineItem, position: Int, view: View)

    fun onClickItem(currentLineItem: LineItem, position: Int, view: View)
}