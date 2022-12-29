package com.example.bce

import com.example.bce.shared.utils.GlobalEncryption
import org.junit.Assert.assertEquals
import org.junit.Test



class GlobalEncryptionTest {
    @Test
    fun password_isSameHash() {
        assertEquals(GlobalEncryption.encryptPassword("Password123!@#$%*&"),
            GlobalEncryption.encryptPassword("Password123!@#$%*&"))
    }

    @Test
    fun password_isNotSame() {
        assertEquals(false, compareHashedPasswords())
    }

    fun compareHashedPasswords() : Boolean {
        return GlobalEncryption.encryptPassword("Password123!@#$%*&") ==
        GlobalEncryption.encryptPassword("Password$%*&")
    }
}