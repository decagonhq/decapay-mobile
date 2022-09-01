package com.decagonhq.decapay.common.utils.validation.inputfieldvalidation

object LoginInputValidation {

    private var EMAIL_PATTERN = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$")
    private val PASSWORD_PATTERN = Regex(".{8,}")

    fun validateUserEmail(email: String): Boolean {
        if (email.isEmpty() || !email.matches(EMAIL_PATTERN)) {
            return false
        }
        return true
    }

    fun validateUserPassword(password: String): Boolean {
        return if (password.isEmpty()) {
            false
        } else if (!password.matches(PASSWORD_PATTERN)) {
            false
        } else {
            true
        }
    }

    fun validateEmailForTextWatcher(email: String): String {
        if (email.isEmpty()) {
            return "Field cannot be empty"
        }
        if (!email.matches(EMAIL_PATTERN)) {
            return "Invalid email"
        }
        return ""
    }

    fun validatePasswordForTextwatcher(passwordText: String): String? {
        if (passwordText.isEmpty()) {
            return "Password cannot be empty"
        }
        if (passwordText.length < 8) {
            return "Password must have a minimum of 8 characters."
        }

        return ""
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        if (password != confirmPassword) {
            return false
        }
        return true
    }
}
