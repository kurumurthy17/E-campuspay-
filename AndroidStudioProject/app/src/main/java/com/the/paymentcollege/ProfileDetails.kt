package com.the.paymentcollege


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.databinding.ActivityProfileDetailsBinding

class ProfileDetails : AppCompatActivity() {
    private lateinit var binding : ActivityProfileDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userCurrent = Firebase.auth.currentUser

        val db = Firebase.firestore
        db.collection("Colleges")
            .get()
            .addOnSuccessListener {result->
                val list = arrayListOf<String>()
                for (document in result){
                    list.add(document.data.get("name").toString())
                }
                val adapter = ArrayAdapter(this,R.layout.list_item,list)
                (binding.CollegeSelector.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
        //Navigate to home
        binding.toHome.setOnClickListener {
            val name = binding.SName.text.toString()
            val college = binding.CollegeSelector.editText?.text.toString()
            val id = userCurrent!!.displayName

            //Show Progress Bar
            binding.progressBarOne.visibility = View.VISIBLE

            //Map User Details
            val userDet = hashMapOf(
                "name" to name,
                "college" to college
            )

            //Check if ID is null.
            if (id != null) {
                db.collection("students")
                    .document(id)
                    .set(userDet, SetOptions.merge())
                    .addOnSuccessListener {
                        binding.progressBarOne.visibility = View.GONE
                        val intent = Intent(this,HomeActivity::class.java)
                        intent.putExtra("id",id)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
            }


        }

        //Navigate Back
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}