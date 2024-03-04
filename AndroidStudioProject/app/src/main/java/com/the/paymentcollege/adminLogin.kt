package com.the.paymentcollege

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.databinding.ActivityAdminLoginBinding

class adminLogin : AppCompatActivity() {
    private lateinit var binding: ActivityAdminLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val sharedPref = getSharedPreferences("hello", Context.MODE_PRIVATE)

        //Navigate Admin HomeClickListener
        binding.toAdminHome.setOnClickListener{
            val adminID = binding.adminCollegeID.text.toString()
            val password = binding.adminPassword.text.toString()

            binding.progressBarThree.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword("$adminID@gmail.com",password)
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        binding.progressBarThree.visibility = View.GONE
                        sharedPref.edit().putString("Type","Admin").apply()
                        val user = auth.currentUser
                        user!!.updateProfile(userProfileChangeRequest {
                            displayName = adminID
                        })
                        val intent = Intent(this,AdminHome::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        Log.w("Error Message",task.exception.toString())
                    }
                }
        }

        //Navigate Back
        binding.backA.setOnClickListener {
            finish()
        }
    }   
}