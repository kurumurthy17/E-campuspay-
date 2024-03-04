package com.the.paymentcollege.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.the.paymentcollege.dataClasses.CurrentPaymentDataClass
import com.the.paymentcollege.dataClasses.ExtraDataClass
import com.the.paymentcollege.databinding.ExtraViewBinding

class ExtrasAdapter(val list: ArrayList<ExtraDataClass>, val onClick: (Int)->Unit,val context:Context) : RecyclerView.Adapter<ExtrasAdapter.ViewHolder>() {
    inner class ViewHolder(val binding : ExtraViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item : ExtraDataClass,position: Int){
            val imageView = binding.extraIcon
            Glide.with(context)
                .load(item.iconUrl)
                .into(imageView)

            binding.extraTitle.text = item.name

            binding.extraContainer.setOnClickListener {
                onClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ExtraViewBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position],position)
    }
}