package com.the.paymentcollege

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    private lateinit var auth : FirebaseAuth
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Initialize Firebase Authentication
        auth = Firebase.auth

        //Access Cloud Firestore
        val db = Firebase.firestore


        //Navigate to On-Boarding
        binding.toGetStarted.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.ScollegeId.text.toString()
            val password = binding.Spassword.text.toString()
            val confirmPassword = binding.SconfirmPassword.text.toString()




            if(password == confirmPassword){
                auth.createUserWithEmailAndPassword("$email@gmail.com",password)
                    .addOnCompleteListener(this){task ->
                        binding.progressBar.visibility = View.GONE
                        if(task.isSuccessful){
                            binding.progressBar.visibility = View.GONE
                            val user = hashMapOf(
                                "studentID" to email
                            )
                            db.collection("students")
                                .document(email)
                                .set(user)

                            val userCurrent = Firebase.auth.currentUser
                            userCurrent!!.updateProfile(userProfileChangeRequest {
                                displayName = email
                            })

                            val intent = Intent(this,ProfileDetails::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(
                                baseContext,
                                "Authentication Failed",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
            else{
                binding.SconfirmPassword.error = "Those passwords didn't match. Try again."
                binding.progressBar.visibility = View.GONE
            }

        }
        var show = true
        //Show Password
        binding.showPass.setOnClickListener {
            if(show){
                binding.Spassword.inputType = InputType.TYPE_CLASS_TEXT
                binding.SconfirmPassword.inputType = InputType.TYPE_CLASS_TEXT
                binding.showPass.text = "Hide Password"
                show = false
            }
            else{
                binding.Spassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.SconfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                show = true
                binding.showPass.text = "Show Password"
            }
        }

        //Navigate Back
        binding.goBack.setOnClickListener {
            finish()
        }
    }
}