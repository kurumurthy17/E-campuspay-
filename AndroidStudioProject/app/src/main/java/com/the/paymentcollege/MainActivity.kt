package com.the.paymentcollege

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val user = Firebase.auth.currentUser
        val sharedPref = getSharedPreferences("hello", Context.MODE_PRIVATE)
        if(user != null){
            val defaultValue = sharedPref.getString("Type","")
            if(defaultValue == "Admin"){
                val intent = Intent(this,AdminHome::class.java)
                startActivity(intent)
                finish()
            }
            if(defaultValue == "Student"){
                val intent =  Intent(this,HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        //Navigate to Student Login Screen
        binding.toStudentLogin.setOnClickListener {
         val intent = Intent(this,Login::class.java)
         startActivity(intent)
        }

        //Navigate to Admin Login Screen
        binding.toAdmin.setOnClickListener {
            val intent = Intent(this, adminLogin::class.java)
            startActivity(intent)
        }
    }
}