package com.the.paymentcollege

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.ViewModels.AdminCurrentViewModel
import com.the.paymentcollege.ViewModels.ExtrasViewModel
import com.the.paymentcollege.ViewModels.PastPaymentsViewModel
import com.the.paymentcollege.adapters.ExtrasAdapter
import com.the.paymentcollege.adapters.PastPaymentsAdapter
import com.the.paymentcollege.adapters.PendingPaymentAdapter
import com.the.paymentcollege.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize Firebase Auth
        auth = Firebase.auth

        //Database Reference
        val db = Firebase.firestore

        //Current User Reference
        val user = auth.currentUser

        // Read User Details
        val docRef = user!!.displayName?.let { db.collection("students").document(it) }

        val userName = binding.studentName
        val studentID = binding.studentID
        val studentCollege = binding.studentCollege


        // Initialize ViewModel (Current Payments)
        val viewModel = ViewModelProvider(this)[AdminCurrentViewModel::class.java]

        // Current Payments List
        viewModel.getCurrentPayments("JPN3624")

        viewModel.getObserver().observe(this, Observer {
            val adapter = PendingPaymentAdapter(it, onClick = {index ->
                val intent = Intent(this,PaymentDetailsActivity::class.java)
                intent.putExtra("ID",it[index].feeID)
                startActivity(intent)
            })
            binding.pendingRecycler.adapter = adapter
            binding.pendingRecycler.layoutManager = LinearLayoutManager(this)
            if(adapter.itemCount == 0){
                binding.emptyPending.visibility = View.VISIBLE
                binding.emptyPending.text = "No Pending Payments"
            }
            else{
                binding.pendingRecycler.visibility = View.VISIBLE
                binding.emptyPending.visibility = View.INVISIBLE
            }
        })


        // Initialize ViewModel (PastPayments)
        val pastViewModel = ViewModelProvider(this)[PastPaymentsViewModel::class.java]

        // Past Payments List
        user.displayName?.let { pastViewModel.getPendingPayments(it) }

        pastViewModel.getObserver().observe(this, Observer {
            val adapter = PastPaymentsAdapter(it)
            binding.pastRecycler.adapter = adapter
            binding.pastRecycler.layoutManager = LinearLayoutManager(this)
            if(adapter.itemCount == 0){
                binding.emptyPast.visibility = View.VISIBLE
                binding.emptyPast.text = "No Past Payments"
            }
            else{
                binding.pastRecycler.visibility = View.VISIBLE
                binding.emptyPast.visibility = View.INVISIBLE
            }
        })

        docRef!!.get()
            .addOnSuccessListener {document->
                if(document != null){
                    val data = document.data
                    userName.text = data!!["name"].toString()
                    studentID.text = data["studentID"].toString()
                    studentCollege.text = data["college"].toString()
                } else{
                    Log.d("ErrorMessage","No Such Document")
                }
            }

        /// Initialize ViewModel (Extras)
        val extrasViewModel =  ViewModelProvider(this)[ExtrasViewModel::class.java]

        // Extras Payments List
        extrasViewModel.getExtras("JPN3624")

        extrasViewModel.getObserver().observe(this, Observer {
            val adapter = ExtrasAdapter(it, onClick = {index ->
                val intent = Intent(this,PaymentDetailsActivity::class.java)
                intent.putExtra("ID",it[index].id)
                intent.putExtra("from","Extras")
                startActivity(intent)
            },this)
            binding.ExtrasRecycler.adapter = adapter
            binding.ExtrasRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        })


        binding.signOutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }




    }
}