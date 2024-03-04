package com.the.paymentcollege

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.the.paymentcollege.adapters.SubjectsAdapter
import com.the.paymentcollege.dataClasses.SubjectDataClass
import com.the.paymentcollege.databinding.FragmentExamRegistrationBinding

class ExamRegistration : Fragment() {

    private lateinit var adapter : SubjectsAdapter
    private lateinit var binding : FragmentExamRegistrationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExamRegistrationBinding.inflate(inflater,container,false)

        adapter = SubjectsAdapter()

        binding.addSubject.setOnClickListener {
            val subjectCode = binding.subjectCodeInput.text.toString()
            val subjectName = binding.subjectNameInput.text.toString()

            adapter.addSubject(SubjectDataClass(subjectName,subjectCode))
        }

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        binding.addedSubRecycler.adapter = adapter
        binding.addedSubRecycler.layoutManager = LinearLayoutManager(activity)


        binding.examSubmitBtn.setOnClickListener {
            val semYear = binding.yearSemInput.text.toString()
            val subjects = adapter.getSubjectList()
            val amount = binding.amountExamInput.text.toString()
            val paymentName = requireActivity().findViewById<EditText>(R.id.paymentNameInput).text.toString()
            val examReg = hashMapOf(
                "YearSem" to semYear,
                "PaymentName" to paymentName,
                "SubjectList" to subjects,
                "amount" to amount
            )

            db.collection("Colleges")
                .document(user!!.displayName.toString())
                .collection("CurrentPayments")
                .document(semYear)
                .set(examReg)
                .addOnSuccessListener {
                    Log.d("Message","Success full")
                }
                .addOnFailureListener { e -> Log.w(" Error Message",e) }

        }

        return binding.root
    }

}