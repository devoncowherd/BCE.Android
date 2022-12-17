package com.example.bce.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.bce.R
import com.example.bce.shared.utils.GlobalToaster
import com.example.bce.shared.viewmodel.AuthViewModel
import com.example.bce.shared.viewmodel.SignUpViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment() {

    private lateinit var firstName : EditText
    private lateinit var lastName : EditText
    private lateinit var address : EditText
    private lateinit var phoneNumber : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var createAccountButton : Button

    private val signUpViewModel : SignUpViewModel by viewModels()
    private val authViewModel : AuthViewModel by viewModels()

    private val TAG = SignUpFragment::class.simpleName

    override fun onDestroyView() {
        super.onDestroyView()

    }

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
        var auth = authViewModel.auth

        createAccountButton = view.findViewById(R.id.signUpButton)

        createAccountButton.setOnClickListener {
            if(checkInputsEmpty(firstName)
                && checkInputsEmpty(lastName)
                && checkInputsEmpty(address)
                && checkInputsEmpty(phoneNumber)
                && checkInputsEmpty(email)
                && checkInputsEmpty(password)
            ){

                if(isValidAccount()){
                    val db = Firebase.firestore

                    val newUser = hashMapOf(
                        "firstName"  to this.firstName.text.toString(),
                        "lastName" to this.lastName.text.toString(),
                        "address" to this.address.text.toString(),
                        "phoneNumber" to this.phoneNumber.text.toString(),
                        "email" to this.email.text.toString(),
                        "password" to this.password.text.toString()
                    )

                    db.collection("users")
                        .add(newUser)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }

                    auth?.createUserWithEmailAndPassword(newUser["email"]!!,
                    newUser["password"]!!)
                        ?.addOnCompleteListener(Activity(), OnCompleteListener<AuthResult> { task ->
                            if(task.isSuccessful){
                                Log.d(TAG,"createUserWithEmail:Success")
                                val user  = auth?.currentUser

                            } else {
                                Log.w(TAG, "createUserWithEmail:Failure", task.exception)
                            }
                        })

                    toastUserVerify()
                    createAccountButton.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                }

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

    fun isValidAccount() : Boolean {
        return true
    }

    fun getViewModelInformation(view : View, viewModel : ViewModel){
    }

}