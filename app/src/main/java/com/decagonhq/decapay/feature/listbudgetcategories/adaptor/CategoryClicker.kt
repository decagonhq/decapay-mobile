package com.decagonhq.decapay.feature.listbudgetcategories.adaptor

import android.view.View
import com.decagonhq.decapay.feature.listbudgetcategories.data.network.model.Data

interface CategoryClicker {

    fun onClickItemEllipsis(currentCategory: Data, position: Int, view: View)
}