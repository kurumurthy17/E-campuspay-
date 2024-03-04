package com.the.paymentcollege.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.dataClasses.CurrentPaymentDataClass

class AdminCurrentViewModel : ViewModel() {
    private val currentPayments = MutableLiveData<ArrayList<CurrentPaymentDataClass>>()

    val db = Firebase.firestore

    fun getCurrentPayments(userName : String) {
        val current = ArrayList<CurrentPaymentDataClass>()
        db.collection("Colleges")
            .document(userName)
            .collection("CurrentPayments")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    current.add(CurrentPaymentDataClass(document.id,document.data["PaymentName"].toString()))
                }
                currentPayments.postValue(current)
            }
            .addOnFailureListener {
                Log.d("Error Message","Error getting Documents",it)
            }
    }

    fun getObserver():MutableLiveData<ArrayList<CurrentPaymentDataClass>>{
        return currentPayments
    }
}