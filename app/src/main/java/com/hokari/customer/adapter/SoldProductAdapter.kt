package com.hokari.customer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hokari.customer.R
import com.hokari.customer.databinding.AddressRowBinding
import com.hokari.customer.databinding.OrderRowBinding

import com.hokari.customer.ui.activities.SoldProductsDetailsActivity
import com.hokari.customer.model.SoldProduct
import com.hokari.customer.utils.LoadGlide



class SoldProductAdapter(private val context: Context, private var soldProductList: ArrayList<SoldProduct>):
    RecyclerView.Adapter<SoldProductAdapter.SoldProductViewHolder>() {

    class SoldProductViewHolder(val binding: OrderRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoldProductViewHolder {
        return SoldProductViewHolder(
            OrderRowBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SoldProductViewHolder, position: Int) {
        val model = soldProductList[position]

        if (holder is SoldProductViewHolder) {

            LoadGlide(context).loadProductImage(
                model.image,
                holder.binding.ivItemImage
            )

            holder.binding.tvItemName.text = model.title
            holder.binding.tvItemPrice.text = "$${model.price}"
            holder.binding.ibDeleteProduct.visibility = View.GONE

            holder.itemView.setOnClickListener {
                val intent = Intent(context, SoldProductsDetailsActivity::class.java)
                intent.putExtra("extra_sold_product_details",model)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return soldProductList.size
    }

}