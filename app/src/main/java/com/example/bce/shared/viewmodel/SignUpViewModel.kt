package com.example.bce.shared.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bce.data.model.User

class SignUpViewModel : ViewModel() {

    var user = User(
        (0..9999).random(),
        "",
        "",
        "",
        "",
    "",
    "",
    "")
}