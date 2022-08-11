package com.decagonhq.decapay.common.utils.resource

class Validator {

    companion object {
        fun validateEmail(email: String): Boolean {

            val emailPattern = Regex("""^(\w+\.?(\w+)?@([a-zA-Z_]+\.){1,2}[a-zA-Z]{2,6})${'$'}""")

            return !(email.isEmpty() || !email.trim().matches(emailPattern))
        }

        fun validateName(name: String): Boolean {
            val allAlphabets = Regex("""^[a-zA-Z-]*${'$'}""")

            return !(name.isEmpty() || !name.trim().matches(allAlphabets))
        }

        fun validatePassword(password: String): Boolean {
            val passwordRegex = Regex(".{8,}")
            return !(password.isEmpty() || !password.trim().matches(passwordRegex))
        }

        fun validateEmailForTextWatcher(email: String): String {
            val emailPattern = Regex("""^(\w+\.?(\w+)?@([a-zA-Z_]+\.){1,2}[a-zA-Z]{2,6})${'$'}""")
            if (email.isEmpty()) {
                return "Field cannot be empty"
            }
            if (!email.trim().matches(emailPattern)) {
                return "Invalid email"
            }
            return ""
        }

        fun validatePhoneNumber(phone: String): Boolean {
            return android.util.Patterns.PHONE.matcher(phone.trim()).matches()
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
    }
}
