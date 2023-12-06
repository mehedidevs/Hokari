package com.hokari.customer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hokari.customer.databinding.OrderRowBinding
import com.hokari.customer.databinding.ProductItemRowBinding
import com.hokari.customer.ui.activities.ProductDetailsActivity
import com.hokari.customer.ui.fragments.ProductFragment


import com.hokari.customer.model.Product
import com.hokari.customer.utils.LoadGlide



open class ProductsListAdapter(private val context: Context, private var list: ArrayList<Product>, private val fragment:
ProductFragment) : RecyclerView.Adapter<ProductsListAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ProductItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductItemRowBinding.inflate(  LayoutInflater.from(context),
                parent,
                false)
        )
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val model = list[position]

        if (holder is ProductViewHolder) {

            LoadGlide(context).loadProductImage(model.image, holder.binding.ivItemImage)

            holder.binding.tvItemName.text = model.title
            holder.binding.tvItemPrice.text = "$${model.price}"


            holder.binding.ibDeleteProduct.setOnClickListener {
                fragment.deleteProduct(model.product_id)

            }

            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra("extra_product_id", model.product_id)
                intent.putExtra("extra_product_owner_id",model.user_id)
                intent.putExtra("extra_edit_perm",1)
                context.startActivity(intent)
            }

        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

}