package com.the.paymentcollege

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.the.paymentcollege.adapters.SubjectsAdapter
import com.the.paymentcollege.dataClasses.SubjectDataClass
import com.the.paymentcollege.databinding.ActivityPaymentDetailsBinding
import org.json.JSONObject
import java.lang.Exception

class PaymentDetailsActivity : AppCompatActivity(),PaymentResultListener{

    private lateinit var binding : ActivityPaymentDetailsBinding
    private lateinit var amount: String
    private lateinit var db : FirebaseFirestore
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Checkout.preload(applicationContext)

        db = Firebase.firestore


        id = intent.getStringExtra("ID").toString()
        val from = intent.getStringExtra("from")

        if(from == "Extras"){
            db.collection("Colleges")
                .document("JPN3624")
                .collection("Extras")
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    binding.pageTitle.text = document.data?.get("PaymentName").toString()
                    binding.paymentTitle.visibility = View.INVISIBLE
                    binding.paymentDes.text = "Description"
                    binding.descriptionText.visibility = View.VISIBLE
                    binding.descriptionText.text = document.data!!["description"].toString()
                    binding.subjectsRecycler.visibility = View.GONE
                    amount = document.data!!["amount"].toString()
                    binding.totalAmount.text = buildString {
                        append("\u20B9")
                        append(amount)
                    }
                }
            binding.payAmount.setOnClickListener {
                startPayment(amount)
            }
        }
        else{
            db.collection("Colleges")
                .document("JPN3624")
                .collection("CurrentPayments")
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    binding.pageTitle.text = document.data?.get("PaymentName").toString()
                    val adapter = SubjectsAdapter()

                    if(document.data!!["SubjectList"] != null){
                        val list  = document.data!!["SubjectList"] as ArrayList<*>
                        list.forEach {
                            val a = it as HashMap<*, *>
                            val item = SubjectDataClass(a["subjectName"].toString(),a["subjectCode"].toString(),)
                            adapter.addSubject(item)

                        }
                        binding.paymentDes.visibility = View.GONE
                    }
                    else{
                        binding.paymentTitle.visibility = View.INVISIBLE
                        binding.paymentDes.text = "Description"
                        binding.descriptionText.visibility = View.VISIBLE
                        binding.descriptionText.text = document.data!!["description"].toString()
                        binding.subjectsRecycler.visibility = View.GONE

                    }
                    binding.subjectsRecycler.adapter = adapter
                    binding.subjectsRecycler.layoutManager = LinearLayoutManager(this)
                    amount = document.data!!["amount"].toString()
                    binding.totalAmount.text = buildString {
                        append("\u20B9")
                        append(amount)
                    }
                }

            binding.payAmount.setOnClickListener {
                startPayment(amount)
            }
        }

        binding.backPaymentDetails.setOnClickListener {
            finish()
        }

    }

    private fun startPayment(amount:String){
        val activity:Activity = this
        val co = Checkout()
        co.setKeyID("rzp_test_zs9Zcgr9mv84jL")
        try {
            val options = JSONObject()
            options.put("name","Fee Payment")
            options.put("theme.color","#3399cc")
            options.put("currency","INR")
            options.put("amount",(amount.toInt() * 100).toString())

            val retryObj = JSONObject()
            retryObj.put("enabled",true)
            retryObj.put("max_count",4)
            options.put("retry",retryObj)

            val prefill = JSONObject()
            prefill.put("email","")
            prefill.put("contact","")

            options.put("prefill",prefill)
            co.open(activity,options)

        }catch (e:Exception){
            Toast.makeText(activity,"Error in payment"+e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

    }

    override fun onPaymentSuccess(p0: String?) {
        val user = Firebase.auth.currentUser
        val data = hashMapOf(
            "amount" to amount,
            "PaymentName" to id,
        )
        user?.displayName?.let {
            db
                .collection("students")
                .document(it)
                .collection("PastPayments")
                .document(id)
                .set(data)
                .addOnSuccessListener { Log.d("Message","Successful") }
                .addOnFailureListener { e -> Log.w("ErrorMessage",e) }

        }
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }
}