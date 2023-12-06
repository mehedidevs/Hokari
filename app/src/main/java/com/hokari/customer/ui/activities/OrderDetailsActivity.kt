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

    private lateinit var binding: ActivityOrderDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActionBar()

        var orderDetails : Order = Order()
        if(intent.hasExtra("extra_order_details")){
            orderDetails = intent.getParcelableExtra<Order>("extra_order_details")!!
        }

        setOrderDetails(orderDetails)

    }

    private fun setOrderDetails(orderDetails: Order){
        binding.tvOrderDetailsId.text = orderDetails.title
        binding.tvOrderDetailsDate.text = dateTimeFormatter(orderDetails)

        binding.rvMyOrderItemsList.layoutManager = LinearLayoutManager(this@OrderDetailsActivity)
        binding.rvMyOrderItemsList.setHasFixedSize(true)

        val cartItemAdapter = CartItemAdapter(this@OrderDetailsActivity,orderDetails.items,false)

        binding.rvMyOrderItemsList.adapter = cartItemAdapter
        binding.tvMyOrderDetailsAddressType.text = orderDetails.address.type
        binding.tvMyOrderDetailsFullName.text = orderDetails.address.fullName
        binding.tvMyOrderDetailsAddress.text =
            "${orderDetails.address.address}, ${orderDetails.address.zipCode}"
        binding.tvMyOrderDetailsAdditionalNote.text = orderDetails.address.additionalNote

        if (orderDetails.address.otherDetails.isNotEmpty()) {
            binding.tvMyOrderDetailsOtherDetails.visibility = View.VISIBLE
            binding.tvMyOrderDetailsOtherDetails.text = orderDetails.address.otherDetails
        } else {
            binding.tvMyOrderDetailsOtherDetails.visibility = View.GONE
        }
        binding.tvMyOrderDetailsMobileNumber.text = orderDetails.address.mobileNumber

        binding.tvOrderDetailsSubTotal.text = orderDetails.sub_total_amount
        binding.tvOrderDetailsSubTotal.text = orderDetails.shipping_charge
        binding.tvOrderDetailsTotalAmount.text = orderDetails.total_amount

    }







    private fun dateTimeFormatter(orderDetails: Order): String {
        val dateFormat = "dd MMM yyyy HH:mm"
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = orderDetails.order_date
        return formatter.format(calendar.time)
    }







    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarMyOrderDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }
        binding.toolbarMyOrderDetailsActivity.setNavigationOnClickListener { onBackPressed() }
    }



}