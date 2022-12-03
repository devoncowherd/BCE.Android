package com.example.bce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val crashButton : AppCompatButton = findViewById(R.id.crashButton)

        crashButton.setOnClickListener {
            throw RuntimeException("Register")
        }
    }

}