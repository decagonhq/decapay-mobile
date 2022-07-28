package com.decagonhq.decapay.feature.signup

import com.decagonhq.decapay.common.utils.resource.Validator
import org.junit.Assert
import org.junit.Test

class ValidatorTest {

    /**
     * Email Validation Tests
     * **/

    @Test
    fun `valid email format`(){
        val email="kingsleyizundu@gmail.com"
        Assert.assertTrue(Validator.validateEmail(email))
    }

    @Test
    fun `email with no domain is invalid`(){
        val email="izundukingsley@"
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }

    @Test
    fun `email with no top level domain is invalid`(){
        val email="izundukingsley@gmail"
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }

    @Test
    fun `email with no at is invalid`(){
        val email="izundukingsley"
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }

    @Test
    fun `email is empty return false`(){
        var email=""
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }

    @Test
    fun `email is only top level domain and subdomain`(){
        val email="@gmail.com"
        Assert.assertFalse(
            Validator.validateEmail(email)
        )
    }


    /**
     * Name Validation Tests
     * **/


    @Test
    fun `valid name`(){
        val name = "David"
        Assert.assertTrue(Validator.validateName(name))
    }

    @Test
    fun `valid name accounts for white space`(){
        val name = " David "
        Assert.assertTrue(Validator.validateName(name))
    }

    @Test
    fun `invalid name accounts for two words`(){
        val name = " David Omu"
        Assert.assertFalse(Validator.validateName(name))
    }

    @Test
    fun `valid name hyphenated`(){
        val name = " David-Omu"
        Assert.assertTrue(Validator.validateName(name))
    }


    @Test
    fun `invalid name special characters`(){
        val name = " David%"
        Assert.assertFalse(Validator.validateName(name))
    }


    /**
     * Passwork Validation Tests
     * **/


   @Test
   fun `valid password`(){
       val password = "1234565Mwe"
        Assert.assertTrue(Validator.validatePassword(password))
   }
    @Test
   fun `invalid password`(){
       val password = "1256"
        Assert.assertFalse(Validator.validatePassword(password))
   }

    @Test
    fun `invalid password empty`(){
       val password = ""
        Assert.assertFalse(Validator.validatePassword(password))
   }
    @Test
    fun `invalid password empty spaces`(){
       val password = "       "
        Assert.assertFalse(Validator.validatePassword(password))
   }
}