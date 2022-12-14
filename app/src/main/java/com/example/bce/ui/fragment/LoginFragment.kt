package com.example.bce.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.bce.R
import com.example.bce.shared.utils.GlobalToaster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private lateinit var signUpButton : Button
    private lateinit var loginButton : Button
    private lateinit var username : EditText
    private lateinit var password : EditText
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_login, container, false)

        loginButton = view.findViewById(R.id.loginButton)
        signUpButton = view.findViewById(R.id.signUpButton)
        username = view.findViewById(R.id.usernameInput)
        password = view.findViewById(R.id.passwordInput)
        auth = Firebase.auth


//        initSignInButton()
//        initAuthViewModel()
//        initGoogleSignInClient()




        loginButton.setOnClickListener{
            if(checkTextBoxEmpty(username, password, requireContext()) > 0){
                    if(this.auth.currentUser == null){
                        println("null")
                        loginButton.findNavController().navigate(R.id.action_loginFragment_to_accountFragment)
                    } else {
                        loginButton.findNavController().navigate(R.id.action_loginFragment_to_accountFragment)
                    }
                }
            }


        //passwordless sign-in? https://firebase.google.com/docs/auth/android/email-link-auth?authuser=1&hl=en
        signUpButton.setOnClickListener {
            signUpButton.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return view;
    }


    fun checkTextBoxEmpty(username : EditText, password: EditText, context : Context) : Int{
        println(username.length())
        if(username.length() <= 0 || password.length() <= 0){
            GlobalToaster.promptFormFulfill(context)
            return -1
        }
        return 1
    }

}