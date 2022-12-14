package com.example.bce.shared.utils

import java.util.regex.Pattern

class GlobalPatternMatcher {
    companion object {
        val NAME_REGEX = "^[A-Za-z'-]{2,}$"
        val PHONE_REGEX = "[0-9]{10,11}"
        val FINAL_PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
        val EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        val ADDRESS_REGEX = "^.{1,}$"
        val LOWERCASE_REGEX = "^(?=.*[a-z]).+$"
        val UPPERCASE_REGEX = "^(?=.*[A-Z]).+$"
        val SPECIAL_CHARACTER_REGEX = "^(?=.*[@!%*?&]).+$"
        val STRING_LENGTH_REGEX = "^.{8,}$"
        val NUMBER_REGEX = "^(?=.*[0-9]).+$"
        val ZIP_REGEX = "^.{5,}$"
        val CITY_REGEX = "^[a-zA-Z'-]{1,}$"

        fun checkValidEmail(email : String) : Boolean {
            val emailPattern = Pattern.compile(EMAIL_REGEX)
            val emailMatcher = emailPattern
                .matcher(email)
            return emailMatcher.matches()
        }

        fun checkNameValid(name : String) : Boolean {
            return Pattern.compile(NAME_REGEX)
                .matcher(name)
                .matches()
        }

        fun checkPhoneNumberValid(phoneNumber : String) : Boolean {
            return Pattern.compile(PHONE_REGEX)
                .matcher(phoneNumber)
                .matches()
        }

        fun checkFinalPasswordValid(password : String) : Boolean {
            return Pattern.compile(FINAL_PASSWORD_REGEX)
                .matcher(password)
                .matches()
        }

        fun checkAddressValid(address : String) : Boolean {
            return Pattern.compile(ADDRESS_REGEX)
                .matcher(address)
                .matches()
        }

        fun checkIncludesLowerCase(string : String) : Boolean {
            return Pattern.compile(LOWERCASE_REGEX)
                .matcher(string)
                .matches()
        }

        fun checkIncludesUpperCase(string : String) : Boolean {
            return Pattern.compile(UPPERCASE_REGEX)
                .matcher(string)
                .matches()
        }

        fun checkIncludesSpecialCharacter(string : String) : Boolean {
            return Pattern.compile(SPECIAL_CHARACTER_REGEX)
                .matcher(string)
                .matches()
        }

        fun checkPasswordLength(string : String) : Boolean {
            return Pattern.compile(STRING_LENGTH_REGEX)
                .matcher(string)
                .matches()
        }

        fun checkIncludesNumber(string : String) : Boolean {
            return Pattern.compile(GlobalPatternMatcher.NUMBER_REGEX)
                .matcher(string)
                .matches()
        }

        fun checkCityValid(city : String) : Boolean {
            return Pattern.compile(CITY_REGEX)
                .matcher(city)
                .matches()
        }

        fun checkZipValid(zip : String) : Boolean {
            return Pattern.compile(ZIP_REGEX)
                .matcher(zip)
                .matches()
        }
    }
}