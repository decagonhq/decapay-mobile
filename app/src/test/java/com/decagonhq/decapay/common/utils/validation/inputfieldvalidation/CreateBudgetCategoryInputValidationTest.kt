package com.decagonhq.decapay.common.utils.validation.inputfieldvalidation

import org.junit.Assert.*
import org.junit.Test

class CreateBudgetCategoryInputValidationTest {

    @Test
    fun validateNameOfCategoryItem_noItemEntered_returnFalse() {
        val inputItem = ""
        val result = CreateBudgetCategoryInputValidation.validateNameOfCategoryItem(inputItem)
        assertFalse(result)
    }

    @Test
    fun validateNameOfCategoryItem_itemLengthLessThanThree_returnFalse() {
        val inputItem = "Go"
        val result = CreateBudgetCategoryInputValidation.validateNameOfCategoryItem(inputItem)
        assertFalse(result)
    }

    @Test
    fun validateNameOfCategoryItem_itemIsLengthThree_returnTrue() {
        val inputItem = "Tax"
        val result = CreateBudgetCategoryInputValidation.validateNameOfCategoryItem(inputItem)
        assertTrue(result)
    }

    @Test
    fun validateNameOfCategoryItem_itemLengthGreaterThanThree_returnTrue() {
        val inputItem = "Test"
        val result = CreateBudgetCategoryInputValidation.validateNameOfCategoryItem(inputItem)
        assertTrue(result)
    }

}