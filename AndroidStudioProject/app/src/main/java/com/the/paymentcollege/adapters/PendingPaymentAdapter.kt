package com.the.paymentcollege.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.paymentcollege.dataClasses.CurrentPaymentDataClass
import com.the.paymentcollege.databinding.PendingPaymentViewBinding

class PendingPaymentAdapter(val list : ArrayList<CurrentPaymentDataClass>,val onClick : (Int)->Unit) : RecyclerView.Adapter<PendingPaymentAdapter.ViewHolder>(){


    inner class ViewHolder(val binding:PendingPaymentViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : CurrentPaymentDataClass,position: Int){
            binding.paymentName.text = item.feeName
            binding.paymentText.text = "pay"

            binding.pendingHolder.setOnClickListener {
                onClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PendingPaymentViewBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position],position)
    }

}