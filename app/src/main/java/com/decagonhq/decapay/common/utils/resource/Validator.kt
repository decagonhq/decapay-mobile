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



        fun validatePassword(password:String):Boolean{
            return  !(password.isEmpty() || password.trim().length <6)
        }


        fun validatePhoneNumber(phone:String):Boolean{
           return  android.util.Patterns.PHONE.matcher(phone.trim()).matches();
        }
    }
}