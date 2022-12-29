package com.example.bce.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bce.R
import com.example.bce.data.model.BCEUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = MainActivity::class.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val db = Firebase.firestore

        val userCollection = db.collection("users")

        if(auth.currentUser != null) {
            Log.d(TAG, auth.uid.toString())

            userCollection
                .document(auth.uid.toString())
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(BCEUser::class.java)

                    Log.d(TAG,user?.email.toString())
                }
        }
    }
}