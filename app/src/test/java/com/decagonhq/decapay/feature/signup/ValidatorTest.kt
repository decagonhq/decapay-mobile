package com.decagonhq.decapay.feature.signup

import com.decagonhq.decapay.common.utils.resource.Validator
import org.junit.Assert
import org.junit.Test

class ValidatorTest {

    /**
     * Email Validation Tests
     * **/

    @Test
    fun valid_email_format(){
        val email="kingsleyizundu@gmail.com"
        Assert.assertTrue(Validator.validateEmail(email))
    }

    @Test
    fun email_with_no_domain_is_invalid(){
        val email="izundukingsley@"
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }

    @Test
    fun email_with_no_topleveldomain_is_invalid(){
        val email="izundukingsley@gmail"
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }

    @Test
    fun email_with_no_at_is_invalid(){
        val email="izundukingsley"
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }

    @Test
    fun email_is_empty_return_false(){
        var email=""
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }

    @Test
    fun email_is_only_top_level_domain_and_subdomain(){
        val email="@gmail.com"
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }


    /**
     * Name Validation Tests
     * **/


    @Test
    fun valid_name(){
        val name = "David"
        Assert.assertTrue(Validator.validateName(name))
    }

    @Test
    fun valid_name_accounts_for_white_space(){
        val name = " David "
        Assert.assertTrue(Validator.validateName(name))
    }

    @Test
    fun invalid_name_accounts_for_two_words(){
        val name = " David Omu"
        Assert.assertFalse(Validator.validateName(name))
    }

    @Test
    fun valid_name_hyphenated(){
        val name = " David-Omu"
        Assert.assertTrue(Validator.validateName(name))
    }


    @Test
    fun invalid_name_special_characters(){
        val name = " David%"
        Assert.assertFalse(Validator.validateName(name))
    }


    /**
     * Passwork Validation Tests
     * **/


   @Test
   fun valid_password(){
       val password = "123456"
        Assert.assertTrue(Validator.validatePassword(password))
   }
    @Test
   fun invalid_password(){
       val password = "1256"
        Assert.assertFalse(Validator.validatePassword(password))
   }

    fun invalid_password_empty(){
       val password = ""
        Assert.assertFalse(Validator.validatePassword(password))
   }
    @Test
    fun invalid_password_empty_spaces(){
       val password = "       "
        Assert.assertFalse(Validator.validatePassword(password))
   }
}