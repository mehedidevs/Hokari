package com.hokari.customer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hokari.customer.R
import com.hokari.customer.databinding.CartItemRowBinding
import com.hokari.customer.databinding.DashboardItemRowBinding
import com.hokari.customer.ui.activities.ProductDetailsActivity

import com.hokari.customer.model.Product
import com.hokari.customer.utils.LoadGlide



open class DashboardListAdapter(private val context: Context, private var list: ArrayList<Product>):
    RecyclerView.Adapter<DashboardListAdapter.DashboardViewHolder>(){

    class DashboardViewHolder(val binding: DashboardItemRowBinding ) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        return DashboardViewHolder(
            DashboardItemRowBinding.inflate(  LayoutInflater.from(context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val model = list[position]

        if (holder is DashboardViewHolder) {
            LoadGlide(context).loadProductImage(model.image, holder.binding.ivDashboardItemImage)
            holder.binding.tvDashboardItemTitle.text = model.title
            holder.binding.tvDashboardItemPrice.text = "$${model.price}"
            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra("extra_product_id",model.product_id)
                intent.putExtra("extra_product_owner_id",model.user_id)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}