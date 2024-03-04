package com.the.paymentcollege.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.dataClasses.ExtraDataClass

class ExtrasViewModel : ViewModel() {

    private val extras = MutableLiveData<ArrayList<ExtraDataClass>>()

    val db = Firebase.firestore

    fun getExtras(userName:String){
        val current = ArrayList<ExtraDataClass>()
        db.collection("Colleges")
            .document(userName)
            .collection("Extras")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result){
                    current.add(ExtraDataClass(doc.data["icon"].toString(),doc.data["PaymentName"].toString(),doc.id))
                }
                extras.postValue(current)
            }
            .addOnFailureListener {
                Log.d("Error Message","Error Getting documents")
            }
    }

    fun getObserver():MutableLiveData<ArrayList<ExtraDataClass>>{
        return extras
    }
}