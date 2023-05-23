package com.bss.firebase_with_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CustomAdapter(private val datalIst: ArrayList<ProductModel>) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val pName = itemview.findViewById<TextView>(R.id.product_name)
        val pPrice = itemview.findViewById<TextView>(R.id.product_price)
        val pImage = itemview.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.productui, parent, false)
        return CustomViewHolder(item)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.pName.text = datalIst[position].productName
        holder.pPrice.text = datalIst[position].productPrice
        Glide.with(holder.pImage.context).load(datalIst[position].productImage).into(holder.pImage)
    }

    override fun getItemCount(): Int {
        return datalIst.size
    }

}