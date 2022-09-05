package com.decagonhq.decapay.feature.createbudgetlineitems.data.network.model

data class CategoryItem(
    val id: Int?,
    val title: String?
) {
    override fun toString(): String {
        return title!!
    }
}