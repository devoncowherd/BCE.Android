package com.example.bce.shared.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bce.data.model.BCEUser
import com.example.bce.shared.utils.GlobalEncryption
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    val TAG = SignUpViewModel::class.simpleName
    val auth = Firebase.auth
    val db = Firebase.firestore
    var firstName = ""
    var lastName = ""
    var address = ""
    var city = ""
    var state = ""
    var zipCode = ""
    var email = ""
    var password = ""
    var phoneNumber = ""


    fun initAddUser(newUser : BCEUser) : Boolean {

        var success = false

        Log.d(TAG ,"inAuthViewModel")

        viewModelScope.launch {
            val auth = Firebase.auth
            val db = Firebase.firestore

            auth.createUserWithEmailAndPassword(newUser.email.toString(),
                GlobalEncryption.encryptPassword(newUser.password.toString()))
                .addOnCompleteListener(){ task ->

                    Log.d(TAG, "We made it to auth")
                    Log.d(TAG,newUser.password.toString())
                    if(task.isSuccessful){

                        newUser.userId = auth.uid.toString()
                        newUser.password = GlobalEncryption
                            .encryptPassword(newUser.password.toString())
                        db.collection("users")
                            .document(auth.uid.toString())
                            .set(newUser)
                            .addOnSuccessListener { documentReference ->
                                success = true
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
                            }
                            .addOnFailureListener { e ->
                                success = false
                                Log.w(TAG, "Error adding document", e)
                            }

                        Log.d(TAG,"createUserWithEmail:Success")

                    } else {
                        success = false
                        Log.w(TAG, "createUserWithEmail:Failure", task.exception)
                    }
                }
        }
        return success
    }
}