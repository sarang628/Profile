package com.sryang.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sarang.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //ProfileActivity.go(this, 32)
    }
}