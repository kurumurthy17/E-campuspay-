package com.the.paymentcollege

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //Navigate to Home
        binding.toHome.setOnClickListener {
            auth = Firebase.auth
            val id = binding.collegeidInput.text.toString()
            val password = binding.passwordInput.text.toString()

            binding.progressBarTwo.visibility = View.VISIBLE

            val sharedPref = getSharedPreferences("hello", Context.MODE_PRIVATE)


            //Sign In
            auth.signInWithEmailAndPassword("$id@gmail.com",password)
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        sharedPref.edit().putString("Type","Student").apply()
                        binding.progressBarTwo.visibility = View.GONE
                        val intent = Intent(this,HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                    else{
                        Log.d("Error Message",task.exception.toString())
                    }
                }

        }

        //Navigate to Sign-Up Screen
        binding.toNewAccount.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        //Navigate Back
        binding.backL.setOnClickListener {
            finish()
        }
    }

}