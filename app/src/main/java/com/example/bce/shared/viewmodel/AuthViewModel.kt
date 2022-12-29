package com.example.bce.shared.viewmodel

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bce.BCEApplication
import com.example.bce.data.model.BCEUser
import com.example.bce.shared.utils.GlobalEncryption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class AuthViewModel(application : BCEApplication) : AndroidViewModel(BCEApplication()){

    private val TAG = AuthViewModel::class.simpleName
    private val auth : FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    //place on coroutine
    fun addUser(newUser : BCEUser, password : String) {

        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(newUser.email.toString(),
                GlobalEncryption.encryptPassword(newUser.password.toString()))
                .addOnCompleteListener(){ task ->

                    Log.d(TAG, "We made it to auth")
                    if(task.isSuccessful){

                        newUser.userId = auth.uid.toString()
                        db.collection("users")
                            .document(auth.uid.toString())
                            .set(newUser)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")

                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                                //GlobalToaster.promptFormFulfill(
                                //DI inject context
                                //)
                            }

                        Log.d(TAG,"createUserWithEmail:Success")

                    } else {
                        Log.w(TAG, "createUserWithEmail:Failure", task.exception)
                    }
            }
        }
    }
}