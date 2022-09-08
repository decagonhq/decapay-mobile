package com.decagonhq.decapay.feature.signup.data.network.model.signupaccountdetails

import java.io.Serializable

data class SignUpAccountDetailsData(
    var firstName: String?,
    var lastName: String?,
    var email: String?,
    var phoneNumber: String?,
    var password: String?
) : Serializable
