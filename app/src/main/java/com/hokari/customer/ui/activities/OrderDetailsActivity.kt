package com.hokari.customer.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hokari.customer.R

import com.hokari.customer.adapter.CartItemAdapter
import com.hokari.customer.databinding.ActivityOrderDetailsBinding
import com.hokari.customer.model.Order

import java.text.SimpleDateFormat
import java.util.*

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        var orderDetails : Order = Order()
        if(intent.hasExtra("extra_order_details")){
            orderDetails = intent.getParcelableExtra<Order>("extra_order_details")!!
        }

        setOrderDetails(orderDetails)

    }

    private fun setOrderDetails(orderDetails: Order){

        binding.apply {
            tvOrderDetailsId.text = orderDetails.title
            tvOrderDetailsDate.text = dateTimeFormatter(orderDetails)

            rvMyOrderItemsList.layoutManager = LinearLayoutManager(this@OrderDetailsActivity)
            rvMyOrderItemsList.setHasFixedSize(true)
        }


        val cartItemAdapter = CartItemAdapter(this@OrderDetailsActivity,orderDetails.items,false)

        binding.apply {
           rvMyOrderItemsList.adapter = cartItemAdapter
            tvMyOrderDetailsAddressType.text = orderDetails.address.type
            tvMyOrderDetailsFullName.text = orderDetails.address.fullName
            tvMyOrderDetailsAddress.text =
                "${orderDetails.address.address}, ${orderDetails.address.zipCode}"
            tvMyOrderDetailsAdditionalNote.text = orderDetails.address.additionalNote
        }


        if (orderDetails.address.otherDetails.isNotEmpty()) {
            binding.tvMyOrderDetailsOtherDetails.visibility = View.VISIBLE
            binding.tvMyOrderDetailsOtherDetails.text = orderDetails.address.otherDetails
        } else {
            binding.tvMyOrderDetailsOtherDetails.visibility = View.GONE
        }
        binding.apply {
            tvMyOrderDetailsMobileNumber.text = orderDetails.address.mobileNumber

            tvOrderDetailsSubTotal.text = orderDetails.sub_total_amount
            tvOrderDetailsShippingCharge.text = orderDetails.shipping_charge
            tvOrderDetailsTotalAmount.text = orderDetails.total_amount
        }


    }







    private fun dateTimeFormatter(orderDetails: Order): String {
        val dateFormat = "dd MMM yyyy HH:mm"
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = orderDetails.order_date
        return formatter.format(calendar.time)
    }







    private fun setupActionBar() {
         lateinit var binding:ActivityOrderDetailsBinding
        setSupportActionBar(binding.toolbarMyOrderDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }
        binding.toolbarMyOrderDetailsActivity.setNavigationOnClickListener { onBackPressed() }
    }



}