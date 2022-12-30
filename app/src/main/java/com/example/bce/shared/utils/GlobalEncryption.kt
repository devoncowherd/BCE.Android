package com.example.bce.shared.utils

import java.security.MessageDigest

class GlobalEncryption {

    companion object {
        fun encryptPassword(password : String) : String {
            val digest = MessageDigest.getInstance("SHA-256")
                .digest(password.toByteArray())

            return bytesToHex(digest)
        }

        //CreditsBaeldung
        private fun bytesToHex(hash : ByteArray) : String {
            val mHash = hash
            var hexString = StringBuilder(2 * hash.size)
            for(byte in hash){
                var hex = Integer.toHexString(0xff and byte.toInt())
                if(hex.length == 1) {
                    hexString.append(0)
                } else {
                    hexString.append(hex)
                }
            }
            return hexString.toString()

        }
    }
}