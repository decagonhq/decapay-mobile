package com.decagonhq.decapay.feature.listbudget.adapter

import android.view.View
import com.decagonhq.decapay.common.data.model.Content

interface BudgetClicker {

    fun onClickItem(currentBudget: Content, position: Int)

    fun onClickItemEllipsis(currentBudget: Content, position: Int, view: View)
}
