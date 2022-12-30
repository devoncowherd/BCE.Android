package com.example.bce.shared.utils

import android.content.Context
import android.widget.Toast

class GlobalToaster() {

    companion object {
        fun promptFormFulfill(context : Context) {
            Toast.makeText(context, "Please Ensure All Fields are Filled Properly.", Toast.LENGTH_LONG).show()
        }

        fun promptVerifyAccount(context : Context) {
            Toast.makeText(context, "Please verify your account.", Toast.LENGTH_LONG).show()
        }

        fun giveGenericError(context : Context){
            Toast.makeText(context, "An Error Occurred. Please Review and Try Again.", Toast.LENGTH_LONG).show()
        }

        fun notifyAccountCreated(context : Context){
            Toast.makeText(context, "Account Created! Please Login.", Toast.LENGTH_LONG).show()
        }

        fun notifyCredentialMismatch(context : Context){
            Toast.makeText(context, "Please Check Credentials and Try Again.", Toast.LENGTH_LONG).show()
        }
    }


}