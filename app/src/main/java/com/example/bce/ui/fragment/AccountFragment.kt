package com.example.bce.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.bce.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AccountFragment : Fragment() {

    private lateinit var logoutButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        val auth = Firebase.auth

        logoutButton = view.findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            auth.signOut()
            view.findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
        }

        return view
    }
}