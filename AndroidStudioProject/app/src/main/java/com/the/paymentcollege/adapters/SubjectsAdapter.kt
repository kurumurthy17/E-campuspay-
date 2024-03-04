package com.the.paymentcollege.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.paymentcollege.dataClasses.SubjectDataClass
import com.the.paymentcollege.dataClasses.SubjectList
import com.the.paymentcollege.databinding.SubjectViewBinding
import javax.security.auth.Subject

class SubjectsAdapter : RecyclerView.Adapter<SubjectsAdapter.ViewHolder>(){

    private var subjectsList = SubjectList()

    fun addSubject(item : SubjectDataClass){
        subjectsList.add(item)
        notifyItemChanged(-1)
    }

    inner class ViewHolder(val binding : SubjectViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(subject : SubjectDataClass){
            binding.subjectCode.text = subject.subjectCode
            binding.subjectName.text = subject.subjectName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SubjectViewBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SubjectsAdapter.ViewHolder, position: Int) {
        holder.bind(subjectsList[position])
    }

    override fun getItemCount(): Int {
        return subjectsList.size
    }


    fun getSubjectList() : SubjectList{
        return subjectsList
    }

}