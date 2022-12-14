package com.example.bce.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.bce.R
import com.example.bce.shared.utils.GlobalToaster


class SignUpFragment : Fragment() {

    private lateinit var firstName : EditText
    private lateinit var lastName : EditText
    private lateinit var address : EditText
    private lateinit var phoneNumber : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var createAccountButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)


        firstName = view.findViewById(R.id.firstNameInputText)
        lastName = view.findViewById(R.id.lastNameInputText)
        address = view.findViewById(R.id.addressInputText)
        phoneNumber = view.findViewById(R.id.phoneNumberInputText)
        password = view.findViewById(R.id.passwordInputText)
        email = view.findViewById(R.id.emailInputText)


        createAccountButton = view.findViewById(R.id.signUpButton)

        createAccountButton.setOnClickListener {
            if(checkInputsEmpty(firstName)
                && checkInputsEmpty(lastName)
                && checkInputsEmpty(address)
                && checkInputsEmpty(phoneNumber)
                && checkInputsEmpty(email)
                && checkInputsEmpty(password)
            ){
                toastUserVerify()
                createAccountButton.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)

            } else {
                GlobalToaster.promptFormFulfill(requireContext())
            }
        }


        return view
    }

    fun toastUserVerify(){
        Toast.makeText(requireContext(), "Please Verify Your Account and Login.", Toast.LENGTH_LONG).show()
    }

    fun checkInputsEmpty(textBox : EditText) : Boolean{
        if(textBox.text == null || textBox.length() == 0){
            return false
        }
        return true
    }

    fun checkFirstName(){

    }

    fun checkLastName(){

    }

    fun checkAddress(){

    }

    fun checkPhoneNumber(){

    }

    fun checkEmail(){

    }

    fun checkPassword() {

    }

}