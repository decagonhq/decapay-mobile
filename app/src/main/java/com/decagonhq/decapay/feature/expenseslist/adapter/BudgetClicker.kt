package com.decagonhq.decapay.feature.expenseslist.adapter

import android.view.View
import com.decagonhq.decapay.common.data.model.Content

interface ExpenseClicker {

    fun onClickItem(currentExpense: Int, position: Int)

    fun onClickItemEllipsis(currentExpense: Int, position: Int, view: View)
}
