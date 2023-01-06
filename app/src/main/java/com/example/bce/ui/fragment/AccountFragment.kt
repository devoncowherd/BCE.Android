package com.example.bce.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bce.R
import com.example.bce.data.model.BCEUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AccountFragment : Fragment() {

    private lateinit var currentUser : BCEUser


    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflater = super.onGetLayoutInflater(savedInstanceState)
        val contextThemeWrapper : Context = ContextThemeWrapper(requireContext(), R.style.Theme_BCE)
        return inflater;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        val auth = Firebase.auth
        val db = Firebase.firestore


        //TODO: Move onto coroutine
        //TODO: Move into VM/Repo
        //TODO: Convert to observable (RXK?)
        //set bottom nav
        //1) account request recycler view
        //2) account details
        //3) user preferences
        db.collection("users")
            .document(auth.uid.toString())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                currentUser = documentSnapshot.toObject(BCEUser::class.java)!!
            }



        return view
    }
}