package com.decagonhq.decapay.feature.listbudgetcategories.adaptor

import android.view.View

interface CategoryClicker {

    fun onClickItemEllipsis(currentBudget: Int, position: Int, view: View)
}