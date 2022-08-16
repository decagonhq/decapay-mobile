package com.decagonhq.decapay.common.utils.validation.inputfieldvalidation

object CreateBudgetCategoryInputValidation {

    fun validateNameOfCategoryItem(item: String): Boolean {
        if (item.length < 3) {
            return false
        }
        return true
    }
}
