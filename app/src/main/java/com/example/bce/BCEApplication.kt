package com.example.bce

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BCEApplication : Application() {

    @Override
    override fun onCreate(){
        super.onCreate()


    }
    companion object {

        //val AUTH : FirebaseAuth = Firebase.auth
    }

}