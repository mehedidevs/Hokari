package com.hokari.customer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hokari.customer.R
import com.hokari.customer.databinding.DashboardItemRowBinding
import com.hokari.customer.databinding.OrderRowBinding
import com.hokari.customer.ui.activities.OrderDetailsActivity

import com.hokari.customer.model.Order
import com.hokari.customer.utils.LoadGlide


class OrderAdapter(private val context: Context,private var list: ArrayList<Order>):
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val  binding: OrderRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            OrderRowBinding.inflate(  LayoutInflater.from(context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        val model = list[position]

        if (holder is OrderViewHolder) {

            LoadGlide(context).loadProductImage(
                model.image,
                holder.binding.ivItemImage
            )

            holder.binding.apply {
               tvItemName.text = model.title
                tvItemPrice.text = "$${model.total_amount}"
                ibDeleteProduct.visibility = View.GONE
            }


            holder.itemView.setOnClickListener {
                val intent = Intent(context, OrderDetailsActivity::class.java)
                intent.putExtra("extra_order_details",model)
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}