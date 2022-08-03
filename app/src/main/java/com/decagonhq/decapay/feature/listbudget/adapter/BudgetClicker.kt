package com.decagonhq.decapay.feature.listbudget.adapter

import android.view.View

interface BudgetClicker {

    fun onClickItem(currentBudget: Int, position:Int)

    fun onClickItemElipsis(currentBudget: Int, position:Int,view: View)
}