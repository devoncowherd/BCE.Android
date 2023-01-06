package com.example.bce.shared.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AccountViewModel : ViewModel() {

    fun getUserRequests() {
        val db = Firebase.firestore

    }
}