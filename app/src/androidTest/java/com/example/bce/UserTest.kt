package com.example.bce

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bce.data.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserTest {

    private lateinit var user : User

    @Before
    fun setUpUser() : User {

        val user =  User(
            "first",
            "last",
            "1111 address",
            "1234578900",
            "email@email.com",
            "password123"
        )

        return user
    }


    @Test
    fun testAddUser() {

        val db = Firebase.firestore

        val user : User = setUpUser()
        val userHashMap = hashMapOf(
            "firstName" to user.firstName,
            "lastName" to user.lastName,
            "address" to user.address,
            "phoneNumber" to user.phoneNumber,
            "email" to user.email,
            "password" to user.password
        )


    }

}