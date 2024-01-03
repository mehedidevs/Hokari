package com.hokari.customer.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hokari.customer.R

import com.hokari.customer.adapter.CartItemAdapter
import com.hokari.customer.database.Database
import com.hokari.customer.databinding.ActivityAddressBinding
import com.hokari.customer.databinding.ActivityCheckoutBinding
import com.hokari.customer.model.Address
import com.hokari.customer.model.Cart
import com.hokari.customer.model.Order
import com.hokari.customer.model.Product
import java.util.*
import kotlin.collections.ArrayList


class CheckoutActivity : UiComponentsActivity() {

    private var lastAddressDetails: Address? = null
    private lateinit var lastProductList: ArrayList<Product>
    private lateinit var lastCartItemList: ArrayList<Cart>
    private var lastSubTotal: Double = 0.0
    private var lastTotal: Double = 0.0
    private lateinit var orderDetails: Order
    lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_checkout)
        setupActionBar()
        getProductList()

        binding.btnPlaceOrder.setOnClickListener {
            placeOrder()
        }

        if(intent.hasExtra("extra_selected_address")){
            lastAddressDetails = intent.getParcelableExtra<Address>("extra_selected_address")
        }

        if(lastAddressDetails != null){

            binding.apply {
                tvCheckoutAddressType.text = lastAddressDetails?.type
                tvCheckoutFullName.text = lastAddressDetails?.fullName
                tvCheckoutAddress.text = "${lastAddressDetails!!.address}, ${lastAddressDetails!!.zipCode}"
                tvCheckoutAdditionalNote.text = lastAddressDetails?.additionalNote
            }


            if (lastAddressDetails?.otherDetails!!.isNotEmpty()) {
                binding.tvCheckoutOtherDetails.text = lastAddressDetails?.otherDetails
            }
            binding.tvCheckoutMobileNumber.text = lastAddressDetails?.mobileNumber

        }


    }

    private fun placeOrder(){
        showProgressBar(getString(R.string.please_wait))
        val uuid = UUID.randomUUID()
        orderDetails = Order(
            Database().getUserID(),
            lastCartItemList,
            lastAddressDetails!!,
            "Order-$uuid",
            lastCartItemList[0].image, //first image will be the order image.
            lastSubTotal.toString(),
            "10.0",
            lastTotal.toString(),
            System.currentTimeMillis()

        )
        Database().createOrder(this,orderDetails)


    }


    fun orderCreatedSuccess(){
        Database().updateProductCartDetails(this,lastCartItemList,orderDetails)
    }

    fun cartDetailsUpdatedSuccessfully(){
        hideProgressBar()
        Toast.makeText(this,getString(R.string.your_order_has_placed_success),Toast.LENGTH_LONG).show()
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    private fun getProductList(){
        showProgressBar(resources.getString(R.string.please_wait))
        Database().getAllProductsList(this@CheckoutActivity)
    }

    fun successProductListFromFS(productList: ArrayList<Product>){
        lastProductList = productList
        getCartItemList()
    }

    private fun getCartItemList(){
        Database().getCartList(this@CheckoutActivity)
    }

    fun successCartItemList(cartList: ArrayList<Cart>){
        hideProgressBar()
        lastCartItemList = cartList

        //rechecking the product stock amount.
        for (product in lastProductList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {
                    cart.stock_quantity = product.stock_quantity
                }
            }
        }

        binding.rvCartListItems.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        binding.rvCartListItems.setHasFixedSize(true)

        val cartListAdapter = CartItemAdapter(this@CheckoutActivity,lastCartItemList,false)
        binding.rvCartListItems.adapter = cartListAdapter

        for(item in lastCartItemList){
            val availableQuantity = item.stock_quantity.toInt()
            if(availableQuantity>0){
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()
                lastSubTotal += (price*quantity)
            }
        }

        binding.tvCheckoutSubTotal.text = "$${lastSubTotal}"
        binding.tvCheckoutShippingCharge.text = "$10.0"
        if(lastSubTotal>0){
           binding.llCheckoutPlaceOrder.visibility = View.VISIBLE
            lastTotal = lastSubTotal+10.0
           binding.tvCheckoutTotalAmount.text = "$$lastTotal"
        }
        else{
            binding.llCheckoutPlaceOrder.visibility = View.GONE
        }

    }



    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarCheckoutActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }

        binding.toolbarCheckoutActivity.setNavigationOnClickListener { onBackPressed() }
    }





}








