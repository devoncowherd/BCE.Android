package com.example.bce.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.bce.R
import com.example.bce.data.model.BCEUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AccountFragment : Fragment() {

    private lateinit var logoutButton : Button
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
        db.collection("users")
            .document(auth.uid.toString())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                currentUser = documentSnapshot.toObject(BCEUser::class.java)!!
            }





        logoutButton = view.findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            auth.signOut()
            view.findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
        }

        return view
    }
}