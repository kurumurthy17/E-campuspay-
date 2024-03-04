package com.the.paymentcollege

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.databinding.FragmentFeePaymentBinding

class FeePayment : Fragment() {

    private lateinit var binding : FragmentFeePaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeePaymentBinding.inflate(inflater,container,false)

        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        binding.conBtn.setOnClickListener {
            val paymentName = requireActivity().findViewById<EditText>(R.id.paymentNameInput).text.toString()
            val amount = binding.editTextText4.text.toString()

            val data = hashMapOf(
                "PaymentName" to paymentName,
                "amount" to amount,
            )

            db.collection("Colleges")
                .document(user!!.displayName.toString())
                .collection("CurrentPayments")
                .document(paymentName)
                .set(data)
                .addOnSuccessListener { Log.d("Message","Successful") }
                .addOnFailureListener { e -> Log.w("ErrorMessage",e) }

        }

        return binding.root
    }

}