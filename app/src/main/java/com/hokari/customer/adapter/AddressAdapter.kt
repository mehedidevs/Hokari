package com.hokari.customer.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hokari.customer.R
import com.hokari.customer.databinding.AddressRowBinding
import com.hokari.customer.model.Address
import com.hokari.customer.ui.activities.CheckoutActivity
import com.hokari.customer.ui.activities.EditAddAddressActivity
import com.hokari.customer.utils.Constants


open class AddressAdapter(
    private val context: Context,
    private var addressList: ArrayList<Address>,
    private val selectAddress: Boolean
) :
    RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    class AddressViewHolder(val binding: AddressRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            AddressRowBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    fun notifyEditItem(activity: Activity, position: Int) {
        val intent = Intent(context, EditAddAddressActivity::class.java)
        intent.putExtra("extra_address_details", addressList[position])
        activity.startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE)
        notifyItemChanged(position)

    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val model = addressList[position]

        holder.binding.apply {
            tvAddressFullName.text = model.fullName
            tvAddressDetails.text = "${model.address}, ${model.zipCode}"
            tvAddressMobileNumber.text = model.mobileNumber
            tvAddressType.text = model.type
        }

        if (selectAddress) {
            holder.itemView.setOnClickListener {
                val selectAddressString: String =
                    (context.getString(R.string.selected_address)) + "${model.address}, ${model.zipCode}"
                Toast.makeText(context, selectAddressString, Toast.LENGTH_LONG).show()
                val intent = Intent(context, CheckoutActivity::class.java)
                intent.putExtra("extra_selected_address", model)
                intent.flags=FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }

        }

    }

    override fun getItemCount(): Int {
        return addressList.size
    }


}