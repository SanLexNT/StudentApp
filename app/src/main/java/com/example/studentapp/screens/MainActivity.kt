package com.example.studentapp.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studentapp.R
import com.example.studentapp.utils.MAIN

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        MAIN = this
    }
}