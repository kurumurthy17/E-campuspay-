package com.the.paymentcollege

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.ViewModels.AdminCurrentViewModel
import com.the.paymentcollege.adapters.CurrentPaymentAdapter
import com.the.paymentcollege.databinding.ActivityAdminHomeBinding

class AdminHome : AppCompatActivity() {
    private lateinit var binding : ActivityAdminHomeBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize Firestore
        val db = Firebase.firestore

        // Get ID
        val id = auth.currentUser!!.displayName.toString()

        // Initialize ViewModel
        val viewModel = ViewModelProvider(this)[AdminCurrentViewModel::class.java]

        // Payments List
        viewModel.getCurrentPayments("JPN3624")

        viewModel.getObserver().observe(this, Observer {
            val adapter = CurrentPaymentAdapter(it)
            binding.currentRecycler.adapter = adapter
            if(adapter.itemCount == 0){
                binding.emptyCurrentP.visibility = View.VISIBLE
                binding.emptyCurrentP.text = "No Current Payments"
            }
            else{
                binding.emptyCurrentP.visibility = View.INVISIBLE
            }
        })



        binding.currentRecycler.layoutManager = LinearLayoutManager(this)


        db.collection("Colleges")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if(document != null){
                    binding.collegeName.text = document.data!!["name"].toString()
                }
            }



        //Navigate to Payment Page
        binding.toAddPayment.setOnClickListener {
            val intent = Intent(this,AddPayment::class.java)
            startActivity(intent)
        }

        // Sign Out Button Admin Page
        binding.signOutA.setOnClickListener {
            val sharedPref = getSharedPreferences("hello", Context.MODE_PRIVATE)
            sharedPref.edit().putString("Type","").apply()
            auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}