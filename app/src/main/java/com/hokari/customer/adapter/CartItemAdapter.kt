package com.hokari.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hokari.customer.R
import com.hokari.customer.database.Database
import com.hokari.customer.databinding.AddressRowBinding
import com.hokari.customer.databinding.CartItemRowBinding
import com.hokari.customer.model.Cart
import com.hokari.customer.ui.activities.CartListActivity
import com.hokari.customer.utils.LoadGlide


open class CartItemAdapter(
    private val context: Context,
    private var itemList: ArrayList<Cart>,
    private val updateCartItems: Boolean
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    class CartItemViewHolder(val binding: CartItemRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            CartItemRowBinding.inflate(  LayoutInflater.from(context),
                parent,
                false)

        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val model = itemList[position]
        LoadGlide(context).loadProductImage(model.image, holder.binding.ivCartItemImage)
        holder.binding.tvCartItemPrice.text = "$${model.price}"
        holder.binding.tvCartItemTitle.text = model.title
        holder.binding.tvCartQuantity.text = model.cart_quantity

        if (model.cart_quantity == "0") {
            holder.binding.ibRemoveCartItem.visibility = View.GONE
            holder.binding.ibAddCartItem.visibility = View.GONE

            if (updateCartItems) {
                holder.binding.ibDeleteCartItem.visibility = View.VISIBLE
            } else {
                holder.binding.ibDeleteCartItem.visibility = View.GONE
            }

            holder.binding.tvCartQuantity.text =
                context.resources.getString(R.string.lbl_out_of_stock)

            holder.binding.tvCartQuantity.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorSnackBarError
                )
            )
        } else {

            if (updateCartItems) {

                holder.binding.ibRemoveCartItem.visibility = View.VISIBLE
                holder.binding.ibAddCartItem.visibility = View.VISIBLE
                holder.binding.ibDeleteCartItem.visibility = View.VISIBLE
            } else {
                holder.binding.apply {
                    ibRemoveCartItem.visibility = View.GONE
                    ibAddCartItem.visibility = View.GONE
                   ibDeleteCartItem.visibility = View.GONE
                }

            }



            holder.binding.tvCartQuantity.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorSecondaryText
                )
            )
        }

        holder.binding.ibDeleteCartItem.setOnClickListener {
            when (context) {
                is CartListActivity -> {
                    context.showProgressBar(context.getString(R.string.please_wait))
                }
            }
            Database().removeItemInCart(context, model.id)

        }

        holder.binding.ibRemoveCartItem.setOnClickListener {
            if (model.cart_quantity == "1") {
                Database().removeItemInCart(context, model.id)
            } else {
                val cartQuantity: Int = model.cart_quantity.toInt()
                val itemHashMap = HashMap<String, Any>()
                itemHashMap["cart_quantity"] = (cartQuantity - 1).toString()
                if (context is CartListActivity) {
                    context.showProgressBar(context.getString(R.string.please_wait))
                }
                Database().updateCartList(context, model.id, itemHashMap)
            }
        }

        holder.binding.ibAddCartItem.setOnClickListener {
            val cartQuantity: Int = model.cart_quantity.toInt()
            if (cartQuantity < model.stock_quantity.toInt()) {
                val itemHashMap = HashMap<String, Any>()
                itemHashMap["cart_quantity"] = (cartQuantity + 1).toString()

                if (context is CartListActivity) {
                    context.showProgressBar(context.getString(R.string.please_wait))
                }
                Database().updateCartList(context, model.id, itemHashMap)
            } else {
                if (context is CartListActivity) {
                    val stringForStock: String =
                        context.getString(R.string.msg_for_available_stock, model.stock_quantity)
                    Toast.makeText(context, stringForStock, Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}