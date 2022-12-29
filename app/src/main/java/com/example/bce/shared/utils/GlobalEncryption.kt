package com.example.bce.shared.utils

import java.security.MessageDigest

class GlobalEncryption {

    companion object {
        fun encryptPassword(password : String) : String {
            return MessageDigest.getInstance("SHA-256")
                .digest(password.toByteArray())
                .toHexString()
        }

        private fun ByteArray.toHexString() : String {
            val hexString = StringBuilder()
            for(byte in this) {
                val hex = Integer.toHexString(0xff and byte.toInt())
                if(hex.length == 1) {
                    hexString.append('0')
                }
            }
            return hexString.toString()
        }
    }
}