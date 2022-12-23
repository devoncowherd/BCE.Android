package com.example.bce.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.bce.R
import com.example.bce.data.model.BCEUser
import com.example.bce.shared.utils.GlobalToaster
import com.example.bce.shared.viewmodel.SignUpViewModel
import com.google.firebase.auth.ktx.auth
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

    //TODO: Implement autocomplete fragment if requested later (prevent junk address)
    //private lateinit var autocompleteFragment : AutoCompleteTextView

    private val signUpViewModel : SignUpViewModel by viewModels()

    private val TAG = SignUpFragment::class.simpleName

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

        var accountValid = false
        var auth = Firebase.auth

        setListeners(this.firstName,
            this.lastName,
            this.address,
            this.phoneNumber,
            this.email,
            this.password
        )

        createAccountButton.setOnClickListener {
            if(checkInputsEmpty(firstName)
                && checkInputsEmpty(lastName)
                && checkInputsEmpty(address)
                && checkInputsEmpty(phoneNumber)
                && checkInputsEmpty(email)
                && checkInputsEmpty(password)
            ){

                if(accountValid){
                    val db = Firebase.firestore

                    val newUser = BCEUser(
                        null,
                        this.firstName.text.toString(),
                        "",
                        this.lastName.text.toString(),
                        this.address.text.toString(),
                        this.phoneNumber.text.toString(),
                        this.email.text.toString(),
                        this.password.text.toString()
                    )

                    auth.createUserWithEmailAndPassword(newUser.email.toString(),
                    newUser.password.toString())
                        .addOnCompleteListener(requireActivity()){ task ->
                            if(task.isSuccessful){

                                newUser.userId = auth.uid.toString()
                                db.collection("users")
                                    .document(auth.uid.toString())
                                    .set(newUser)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }

                                Log.d(TAG,"createUserWithEmail:Success")

                            } else {
                                Log.w(TAG, "createUserWithEmail:Failure", task.exception)
                            }
                        }

                    GlobalToaster.promptVerifyAccount(requireContext())
                    createAccountButton.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                }

            } else {
                GlobalToaster.promptFormFulfill(requireContext())
            }
        }


        return view
    }


    private fun checkInputsEmpty(textBox : EditText) : Boolean{
        if(textBox.text == null || textBox.length() == 0){
            return false
        }
        return true
    }


    private fun validateFirstName(editText : EditText) : Boolean{

        editText.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO: Upon text-change, check if the String is a valid entry and pass it to the ViewModel
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        })

        return false
    }

    private fun setListeners(firstName : EditText,
                                lastName : EditText,
                                address : EditText,
                                phoneNumber : EditText,
                                email : EditText,
                                password : EditText
                                ) {

        //TODO: Create all listeners for the EditTexts
        //validateFirstName()
    }



}