package com.the.paymentcollege

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.the.paymentcollege.adapters.SubjectsAdapter
import com.the.paymentcollege.databinding.ActivityAddPaymentBinding


class AddPayment : AppCompatActivity() {

    private lateinit var binding : ActivityAddPaymentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Navigate Back
        binding.backAdd.setOnClickListener {
            finish()
        }

        //Payment Type Selector Array
        val items = listOf("Exam Registration","Extras")


        //Adapter to the dropdown menu
        val adapter = ArrayAdapter(this,R.layout.list_item,items)
        val selector = binding.paymentTypeSelctor.editText as AutoCompleteTextView

        selector.setAdapter(adapter)

        selector.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, viewA, position, p3 ->
                val selectedValue = adapterView.getItemAtPosition(position)
                when(selectedValue){
                    "Exam Registration" ->{
                        supportFragmentManager.commit {
                            replace<ExamRegistration>(R.id.paymentFrame)
                            setReorderingAllowed(true)
                            addToBackStack("name")
                        }
                    }
                    "Extras" ->{
                        supportFragmentManager.commit {
                            replace<FeePayment>(R.id.paymentFrame)
                            setReorderingAllowed(true)
                            addToBackStack("")
                        }
                    }
                }
            }
    }
}