package com.decagonhq.decapay.feature.budgetdetails.adaptor

import android.view.View

interface LineItemClicker {

    fun onClickItemEllipsis(currentBudget: Int, position: Int, view: View)
    fun onClickItemLog(currentBudget: Int, position: Int, view: View)
}