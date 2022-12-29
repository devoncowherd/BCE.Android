package com.example.bce

import com.example.bce.shared.utils.GlobalPatternMatcher
import org.junit.Assert.assertEquals
import org.junit.Test


class GlobalPatternMatcherTest {

    @Test
    fun test_passTest() {
        assertEquals(true,true)
    }

    @Test
    fun name_isValid(){
        val name : String = "Jane"
        assertEquals(true, GlobalPatternMatcher.checkNameValid(name))
    }

    @Test
    fun email_isValid() {
        assertEquals(true,GlobalPatternMatcher.checkValidEmail("validEmail@gmail.com") )
    }

    @Test
    fun email_isNotValid() {
        assertEquals(false,GlobalPatternMatcher.checkValidEmail("trolololol@@gg.face"))
    }

    @Test
    fun password_isValid() {
        assertEquals(true, GlobalPatternMatcher.checkFinalPasswordValid("t!0ps3cRETp@$$"))
    }

    @Test
    fun password_isNotValid() {
        assertEquals(false, GlobalPatternMatcher.checkFinalPasswordValid("password123"))
    }

    @Test
    fun phoneNumber_isValid() {
        assertEquals(true, GlobalPatternMatcher.checkPhoneNumberValid("6054756968"))
    }

    @Test
    fun phoneNumber_isNotValid() {
        assertEquals(false, GlobalPatternMatcher.checkPhoneNumberValid("556054756968"))
        assertEquals(false, GlobalPatternMatcher.checkPhoneNumberValid("605 475-6968"))
    }

    @Test
    fun password_requirementsMet() {
        assertEquals(true, GlobalPatternMatcher.checkIncludesLowerCase("aa##Ba"))
        assertEquals(true, GlobalPatternMatcher.checkIncludesUpperCase("AA32aA"))
        assertEquals(true, GlobalPatternMatcher.checkIncludesSpecialCharacter("@!!av"))
        assertEquals(true, GlobalPatternMatcher.checkPasswordLength("aaab2228"))
        assertEquals(true, GlobalPatternMatcher.checkIncludesNumber("55aaa!Sa"))
    }

    @Test
    fun zipCode_isValid() {
        assertEquals(true, GlobalPatternMatcher.checkZipValid("12345"))
    }

}