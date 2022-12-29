package com.example.bce.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bce.R
import com.example.bce.data.model.BCEUser
import com.example.bce.shared.utils.GlobalPatternMatcher
import com.example.bce.shared.utils.GlobalToaster
import com.example.bce.shared.viewmodel.AuthViewModel
import com.example.bce.shared.viewmodel.SignUpViewModel
import com.example.bce.ui.adapter.StateSpinnerAdapter
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
    private lateinit var state : Spinner
    private lateinit var stateArray : Array<String>
    private lateinit var zipCode : EditText
    private lateinit var city : EditText
    private lateinit var selectedState : TextView

    private lateinit var firstNameWarning : TextView
    private lateinit var lastNameWarning : TextView
    private lateinit var addressWarning : TextView
    private lateinit var phoneNumberWarning : TextView
    private lateinit var emailWarning : TextView
    private lateinit var passwordWarning : TextView
    private lateinit var passwordLengthWarning : TextView
    private lateinit var passwordLowerWarning : TextView
    private lateinit var passwordUpperWarning : TextView
    private lateinit var passwordNumberWarning : TextView
    private lateinit var passwordSpecialWarning : TextView


    //TODO: Implement autocomplete fragment if requested later (prevent junk address)
    //private lateinit var autocompleteFragment : AutoCompleteTextView

    private val authViewModel : AuthViewModel by viewModels()
    private val signUpViewModel : SignUpViewModel by viewModels()
    private val TAG = SignUpFragment::class.simpleName

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflater = super.onGetLayoutInflater(savedInstanceState)
        val contextThemeWrapper : Context = ContextThemeWrapper(requireContext(), R.style.Theme_BCE)
        return inflater;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)


        firstName = view.findViewById(R.id.firstNameInputText)
        lastName = view.findViewById(R.id.lastNameInputText)
        address = view.findViewById(R.id.addressInputText)
        phoneNumber = view.findViewById(R.id.phoneNumberInputText)
        password = view.findViewById(R.id.passwordInputText)
        email = view.findViewById(R.id.emailInputText)
        createAccountButton = view.findViewById(R.id.signUpButton)
        city = view.findViewById(R.id.cityInputText)
        zipCode = view.findViewById(R.id.zipInputText)
        state = view.findViewById(R.id.stateSpinner)
        stateArray = resources.getStringArray(R.array.states_array)
        selectedState = view.findViewById(R.id.selectedState)

        val stateSpinnerAdapter = StateSpinnerAdapter(requireContext(),
        R.layout.spinner_item_layout,
        stateArray.toList())

        state.adapter = stateSpinnerAdapter

        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                    signUpViewModel.state = parent?.getItemAtPosition(position).toString()
                    selectedState.text = signUpViewModel.state
                    //state.selectedItem = parent?.getItemAtPosition(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }

        }


        firstNameWarning  = view.findViewById(R.id.firstNameWarning)
        lastNameWarning = view.findViewById(R.id.lastNameWarning)
        addressWarning = view.findViewById(R.id.addressWarning)
        phoneNumberWarning = view.findViewById(R.id.phoneNumberWarning)
        emailWarning = view.findViewById(R.id.emailWarning)
        passwordWarning = view.findViewById(R.id.passwordWarning)
        passwordLengthWarning = view.findViewById(R.id.passwordRequirementLength)
        passwordLowerWarning = view.findViewById(R.id.passwordRequirementLower)
        passwordUpperWarning = view.findViewById(R.id.passwordRequirementUpper)
        passwordNumberWarning = view.findViewById(R.id.passwordRequirementNumber)
        passwordSpecialWarning = view.findViewById(R.id.passwordRequirementSpecial)

        var accountValid = false
        var auth = Firebase.auth

        firstName.setText(signUpViewModel.firstName)
        lastName.setText(signUpViewModel.lastName)
        address.setText(signUpViewModel.address)
        email.setText(signUpViewModel.email)
        phoneNumber.setText(signUpViewModel.phoneNumber)


        validateAddress(address, addressWarning )
        validateFirstName(firstName, firstNameWarning)
        validateLastName(lastName, lastNameWarning)
        validatePhoneNumber(phoneNumber, phoneNumberWarning)
        validateEmail(email, emailWarning)
        validatePassword(password,
                        passwordWarning,
                        passwordLowerWarning,
                        passwordUpperWarning,
                        passwordLengthWarning,
                        passwordSpecialWarning,
                        passwordNumberWarning
        )

        createAccountButton.setOnClickListener {

            Log.d(TAG,"Button Pressed!")
            if(checkInputsEmpty(this.firstName)
                && checkInputsEmpty(this.lastName)
                && checkInputsEmpty(this.address)
                && checkInputsEmpty(this.phoneNumber)
                && checkInputsEmpty(this.email)
                && checkInputsEmpty(this.password)
                && checkInputsEmpty(this.zipCode)
                && checkInputsEmpty(this.city)
                && signUpViewModel.state != ""
            ){
                Log.d(TAG, "isValid")

                if(accountValid){
                    val db = Firebase.firestore

                    Log.d(TAG, "isValid")

                    val newUser = BCEUser(
                        null,
                        signUpViewModel.firstName,
                        signUpViewModel.lastName,
                        signUpViewModel.address,
                        signUpViewModel.city,
                        signUpViewModel.state,
                        signUpViewModel.zipCode,
                        signUpViewModel.phoneNumber,
                        signUpViewModel.email,
                        signUpViewModel.password
                    )

                    //TODO: REVIEW
                    authViewModel.addUser(newUser,
                    signUpViewModel.password)

                    GlobalToaster.promptVerifyAccount(requireContext())
                }

            } else {
                GlobalToaster.promptFormFulfill(requireContext())
            }
        }


        return view
    }

    private fun checkInputsEmpty(textBox : EditText) : Boolean {
        if(textBox.text == null || textBox.length() == 0){
            return false
        }
        return true
    }


    private fun validateZipCode(zip : EditText) {
        zip.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                signUpViewModel.zipCode = zip.text.toString()
                if(GlobalPatternMatcher.checkZipValid(signUpViewModel.zipCode)) {

                }
                //TODO("Add Form Validations for ZIP)
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

        })
    }

    private fun validatePassword(password : EditText,
                                 passwordWarning : TextView,
                                 passwordLowerWarning : TextView,
                                 passwordUpperWarning : TextView,
                                 passwordLengthWarning : TextView,
                                 passwordSpecialWarning : TextView,
                                 passwordNumberWarning : TextView
    ) {

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpViewModel.password = password.text.toString()

                if(GlobalPatternMatcher.checkFinalPasswordValid(signUpViewModel.password)){
                    passwordWarning.visibility = View.GONE
                    passwordLowerWarning.visibility = View.GONE
                    passwordSpecialWarning.visibility = View.GONE
                    passwordUpperWarning.visibility = View.GONE
                    passwordLengthWarning.visibility = View.GONE
                    passwordNumberWarning.visibility = View.GONE

                } else {
                    passwordWarning.visibility = View.VISIBLE

                    if(GlobalPatternMatcher.checkIncludesLowerCase(signUpViewModel.password)){
                        passwordLowerWarning.visibility = View.GONE
                    } else {
                        passwordLowerWarning.visibility = View.VISIBLE
                    }

                    if(GlobalPatternMatcher.checkIncludesSpecialCharacter(signUpViewModel.password)){
                        passwordSpecialWarning.visibility = View.GONE
                    } else {
                        passwordSpecialWarning.visibility = View.VISIBLE
                    }

                    if(GlobalPatternMatcher.checkIncludesUpperCase(signUpViewModel.password)){
                        passwordUpperWarning.visibility = View.GONE
                    } else {
                        passwordUpperWarning.visibility = View.VISIBLE
                    }

                    if(GlobalPatternMatcher.checkPasswordLength(signUpViewModel.password)){
                        passwordLengthWarning.visibility = View.GONE
                    } else {
                        passwordLengthWarning.visibility = View.VISIBLE
                    }

                    if(GlobalPatternMatcher.checkIncludesNumber(signUpViewModel.password)){
                        passwordNumberWarning.visibility = View.GONE
                    } else {
                        passwordNumberWarning.visibility = View.VISIBLE
                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }
    private fun validateEmail(email :EditText, warning : TextView) {
        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpViewModel.email = email.text.toString()

                if(GlobalPatternMatcher.checkValidEmail(signUpViewModel.email)){
                    warning.visibility = View.GONE
                } else {
                    warning.visibility = View.VISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun validatePhoneNumber(phoneNumber : EditText, warning : TextView) {
        phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpViewModel.phoneNumber = phoneNumber.text.toString()

                if(GlobalPatternMatcher.checkPhoneNumberValid(signUpViewModel.phoneNumber)){
                    warning.visibility = View.GONE
                } else {
                    warning.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun validateAddress(address : EditText, warning : TextView) {
        address.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpViewModel.address = address.text.toString()

                if(GlobalPatternMatcher.checkAddressValid(signUpViewModel.address)){
                    warning.visibility = View.GONE
                } else {
                    warning.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun validateLastName(lastName : EditText, warning : TextView) {
        lastName.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                signUpViewModel.lastName = lastName.text.toString()

                if(GlobalPatternMatcher.checkNameValid(signUpViewModel.lastName)){
                    warning.visibility = View.GONE
                } else {
                    warning.visibility = View.VISIBLE
                }


            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }


    private fun validateFirstName(firstName : EditText, warning : TextView) : Boolean{

        firstName.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpViewModel.firstName = firstName.text.toString()

                if(GlobalPatternMatcher.checkNameValid(signUpViewModel.firstName)){
                    warning.visibility = View.GONE
                } else {
                    warning.visibility = View.VISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        return false
    }



}