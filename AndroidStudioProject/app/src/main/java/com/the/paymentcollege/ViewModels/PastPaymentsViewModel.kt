package com.the.paymentcollege.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.dataClasses.CurrentPaymentDataClass

class PastPaymentsViewModel : ViewModel() {
    private val pastPayments = MutableLiveData<ArrayList<CurrentPaymentDataClass>>()

    val db = Firebase.firestore

    fun getPendingPayments(userName:String){
        val past = ArrayList<CurrentPaymentDataClass>()
        db.collection("students")
            .document(userName)
            .collection("PastPayments")
            .get()
            .addOnSuccessListener {result ->
                for (document in result){
                    past.add(CurrentPaymentDataClass(document.id,document.data["PaymentName"].toString()))
                }
                pastPayments.postValue(past)
            }
    }

    fun getObserver(): MutableLiveData<ArrayList<CurrentPaymentDataClass>>{
        return pastPayments
    }

}