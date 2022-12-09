package com.example.bce

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.bce.data.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddUserTest {

    companion object{
        private val TAG : String? = AddUserTest::class.simpleName
    }

    @Before
    fun firestoreInit(){

        val context : Context = InstrumentationRegistry
            .getInstrumentation().targetContext
        Firebase.initialize(
            context
        )
    }

    @Test
    fun testUserAdded(){
        assertEquals(1,addTestUserToFirebase())
    }

    fun createTestUserObject() : User {

        val user =  User(
            Int.MAX_VALUE,
            "first",
            "last",
            "1111 address",
            "1234578900",
            "email@email.com",
            "password123"
        )

        return user
    }

    fun createUserHashMap() : HashMap<String, Any> {

        val user = createTestUserObject()
        val userHashMap : HashMap<String, Any> = hashMapOf(
            "userId" to user.userId,
            "firstName" to user.firstName,
            "lastName" to user.lastName,
            "address" to user.address,
            "phoneNumber" to user.phoneNumber,
            "email" to user.email,
            "password" to user.password
        )
        return userHashMap
    }

    fun addTestUserToFirebase() : Int {

        val db = Firebase.firestore

        var state = 0

        db.collection("users")
            .add(createUserHashMap())
            .addOnSuccessListener { documentReference ->
                Log.d(TAG,"DocumentSnapshot added with ID: ${documentReference.id}")
                Log.v(TAG, "Fake User Added")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        state = 1
        return state
    }
}