package com.the.paymentcollege.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.the.paymentcollege.ViewModels.AdminCurrentViewModel
import com.the.paymentcollege.dataClasses.CurrentPaymentDataClass
import com.the.paymentcollege.databinding.CompletedPaymentViewBinding

class CurrentPaymentAdapter(val list : ArrayList<CurrentPaymentDataClass>): RecyclerView.Adapter<CurrentPaymentAdapter.ViewHolder>(){




    inner class ViewHolder(val binding:CompletedPaymentViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : CurrentPaymentDataClass){
            binding.completedText.text = item.feeName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CompletedPaymentViewBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}