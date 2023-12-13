package com.hokari.customer.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hokari.customer.R
import com.hokari.customer.databinding.ActivityProductDetailsBinding
import com.hokari.customer.databinding.ActivitySoldProductsBinding
import com.hokari.customer.databinding.ActivitySoldProductsDetailsBinding

import com.hokari.customer.model.SoldProduct
import com.hokari.customer.utils.LoadGlide

import java.text.SimpleDateFormat
import java.util.*

class SoldProductsDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySoldProductsDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySoldProductsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        var productDetails : SoldProduct = SoldProduct()

        if(intent.hasExtra("extra_sold_product_details")){
            productDetails = intent.getParcelableExtra<SoldProduct>("extra_sold_product_details")!!
        }

        fillDetails(productDetails)

    }







    private fun fillDetails(productDetails: SoldProduct) {

        binding.tvSoldProductDetailsId.text = productDetails.order_id

        val dateFormat = "dd MMM yyyy HH:mm"
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = productDetails.order_date
        binding.tvSoldProductDetailsDate.text = formatter.format(calendar.time)

        LoadGlide(this@SoldProductsDetailsActivity).loadProductImage(
            productDetails.image,
            binding.ivProductItemImage
        )

        binding.apply {
            tvSoldProductQuantity.text = productDetails.sold_quantity
            tvProductItemName.text = productDetails.title
           tvProductItemPrice.text ="$${productDetails.price}"


            tvSoldDetailsAddressType.text = productDetails.address.type
            tvSoldDetailsFullName.text = productDetails.address.fullName
            tvSoldDetailsAddress.text =
                "${productDetails.address.address}, ${productDetails.address.zipCode}"
            tvSoldDetailsAdditionalNote.text = productDetails.address.additionalNote
        }


        if (productDetails.address.otherDetails.isNotEmpty()) {
            binding.tvSoldDetailsOtherDetails.visibility = View.VISIBLE
            binding.tvSoldDetailsOtherDetails.text = productDetails.address.otherDetails
        } else {
            binding.tvSoldDetailsOtherDetails.visibility = View.GONE
        }
        binding.apply {
            tvSoldDetailsMobileNumber.text = productDetails.address.mobileNumber

           tvSoldProductSubTotal.text = productDetails.sub_total_amount
            tvSoldProductShippingCharge.text = productDetails.shipping_charge
            tvSoldProductTotalAmount.text = productDetails.total_amount
        }

    }






    private fun setupActionBar() {
        lateinit var binding: ActivitySoldProductsDetailsBinding
        binding = ActivitySoldProductsDetailsBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbarSoldProductDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }
        binding.toolbarSoldProductDetailsActivity.setNavigationOnClickListener { onBackPressed() }
    }



}