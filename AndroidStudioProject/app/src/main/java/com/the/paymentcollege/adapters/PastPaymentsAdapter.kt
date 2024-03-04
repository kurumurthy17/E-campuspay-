package com.the.paymentcollege.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.paymentcollege.dataClasses.CurrentPaymentDataClass
import com.the.paymentcollege.databinding.PastPaymentViewBinding

class PastPaymentsAdapter (val list : ArrayList<CurrentPaymentDataClass>):RecyclerView.Adapter<PastPaymentsAdapter.ViewHolder>(){

    inner class ViewHolder(val binding:PastPaymentViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item : CurrentPaymentDataClass){
            binding.pastPName.text = item.feeName
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PastPaymentsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PastPaymentViewBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PastPaymentsAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}