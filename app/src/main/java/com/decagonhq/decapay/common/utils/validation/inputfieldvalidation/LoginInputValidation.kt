package com.decagonhq.decapay.common.utils.validation.inputfieldvalidation

object LoginInputValidation {

    private var EMAIL_PATTERN = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$")
    private val PASSWORD_PATTERN = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")

    fun validateUserEmail(email: String): Boolean {
        if (email.isEmpty() || !email.matches(EMAIL_PATTERN)){
            return false
        }
        return true
    }

    fun validateUserPassword(password: String): Boolean {
        return if (password.isEmpty()){
            false
        } else if (!password.matches(PASSWORD_PATTERN)){
            false
        } else {
            true
        }
    }

    fun validateEmailForTextWatcher(email: String): String {
        if (email.isEmpty()){
            return "Field cannot be empty"
        }
        if (!email.matches(EMAIL_PATTERN)){
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
        if (!passwordText.matches(".*[0-9].*".toRegex())) {
            return "Password must contain at least 1 number."
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Password must contain at least 1 upper case character."
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Password must contain at least 1 lower case character."
        }
        if (!passwordText.matches(".*[`~!@#$%^&*()\\-_=+|}{\\]\\[\"\';:?/>.<,].*".toRegex())) {
            return "Password must contain at least 1 special character (@#$%&?!)."
        }
        return ""
    }
}
