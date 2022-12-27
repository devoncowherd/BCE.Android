package com.example.bce.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.bce.R
import com.example.bce.shared.utils.GlobalToaster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private lateinit var signUpButton : Button
    private lateinit var loginButton : Button
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var auth : FirebaseAuth

    private val TAG = LoginFragment::class::simpleName

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
        val view : View = inflater.inflate(R.layout.fragment_login, container, false)

        loginButton = view.findViewById(R.id.loginButton)
        signUpButton = view.findViewById(R.id.signUpButton)
        email = view.findViewById(R.id.emailInput)
        password = view.findViewById(R.id.passwordInput)
        auth = Firebase.auth

        if(auth.currentUser != null){
            findNavController().navigate(R.id.action_loginFragment_to_accountFragment)
        }

        loginButton.setOnClickListener{
            if(checkTextBoxEmpty(this.email,this.password)){
                auth.signInWithEmailAndPassword(
                    this.email.text.toString(),
                    this.password.text.toString())
                    .addOnCompleteListener(requireActivity()){ task ->
                        if(task.isSuccessful) {
                            Log.d(TAG.toString(), "Login Successful")
                            it.findNavController().navigate(R.id.action_loginFragment_to_accountFragment)
                        } else {
                            Log.d(TAG.toString(), "Login Unsuccessful")
                        }
                    }
            } else {
                GlobalToaster.promptFormFulfill(requireContext())
            }
        }


        //passwordless sign-in? https://firebase.google.com/docs/auth/android/email-link-auth?authuser=1&hl=en
        signUpButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return view;
    }


    fun checkTextBoxEmpty(email : EditText, password: EditText) : Boolean{
        if(email.length() <= 0 || password.length() <= 0){
            return false
        }
        return true
    }
}