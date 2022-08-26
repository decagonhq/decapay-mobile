package com.decagonhq.decapay.feature.expenseslist.adapter

import android.view.View
import com.decagonhq.decapay.feature.expenseslist.data.network.model.ExpenseContent

interface ExpenseClicker {

    fun onClickItem(currentExpense: ExpenseContent, position: Int)

    fun onClickItemEllipsis(currentExpense: ExpenseContent, position: Int, view: View)
}
