package com.decagonhq.decapay.common.utils.validation.inputfieldvalidation

import org.junit.Assert.* // ktlint-disable no-wildcard-imports
import org.junit.Test

class LoginInputValidationTest {

    @Test
    fun validateUserEmail_anEmptyEmailField_returnFalse() {
        val emailField = ""
        val result = LoginInputValidation.validateUserEmail(emailField)
        assertFalse(result)
    }

    @Test
    fun validateUserEmail_givenEmailHasNoAtSymbol_returnFalse() {
        val receivedEmail = "kolagmail.com"
        val result = LoginInputValidation.validateUserEmail(receivedEmail)
        assertFalse(result)
    }

    @Test
    fun validateUserEmail_givenEmailHasNoLetterAfterDot_returnFalse() {
        val receivedEmail = "kola@gmail."
        val result = LoginInputValidation.validateUserEmail(receivedEmail)
        assertFalse(result)
    }

    @Test
    fun validateUserEmail_givenACorrectEmail_returnTrue() {
        val receivedEmail = "kola@gmail.com"
        val result = LoginInputValidation.validateUserEmail(receivedEmail)
        assertEquals(true, result)
    }

    @Test
    fun validateUserPassword_givenPasswordIsLessThanEightCharacters_returnFalse() {
        val receivedPassword = "Cho*01"
        val result = LoginInputValidation.validateUserPassword(receivedPassword)
        assertFalse(result)
    }

    @Test
    fun validateUserPassword_givenPasswordHasNoSpecialCharacter_returnFalse() {
        val receivedPassword = "Chosenone1"
        val result = LoginInputValidation.validateUserPassword(receivedPassword)
        assertFalse(result)
    }

}
