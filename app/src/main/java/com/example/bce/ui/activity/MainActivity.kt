package com.example.bce.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bce.R
import com.example.bce.shared.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val authViewModel: AuthViewModel by viewModels()
    private val TAG = MainActivity::class.simpleName



    public override fun onStart(){
        super.onStart()

        var currentUser : FirebaseUser? = auth.currentUser
        if(currentUser == null){
            //
        } else {
            //reload()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        authViewModel.auth = this.auth
        val db = Firebase.firestore

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error getting documents.", e)
            }
    }
}