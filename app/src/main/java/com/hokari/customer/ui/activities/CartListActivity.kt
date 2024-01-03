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
import com.hokari.customer.databinding.ActivityCartListBinding
import com.hokari.customer.model.Cart
import com.hokari.customer.model.Product


class CartListActivity : UiComponentsActivity() {


    private lateinit var lastProductsList: ArrayList<Product>
    private lateinit var lastCartItems: ArrayList<Cart>

    lateinit var  binding: ActivityCartListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.btnCheckout.setOnClickListener {
            val intent = Intent(this@CartListActivity, AddressActivity::class.java)
            intent.putExtra("extra_select_address",true)
            startActivity(intent)
        }
    }



    fun successCartItemList(cartList: ArrayList<Cart>){
        hideProgressBar()




        for (product in lastProductsList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {

                    cart.stock_quantity = product.stock_quantity

                    if (product.stock_quantity.toInt() == 0){
                        cart.cart_quantity = product.stock_quantity
                    }
                }
            }
        }



        lastCartItems = cartList



        if (lastCartItems.size > 0) {

            binding.apply {

                rvCartItemsList.visibility = View.VISIBLE
                binding.llCheckout.visibility = View.VISIBLE
                binding.tvNoCartItemFound.visibility = View.GONE

                rvCartItemsList.layoutManager = LinearLayoutManager(this@CartListActivity)
                rvCartItemsList.setHasFixedSize(true)

                val cartListAdapter = CartItemAdapter(this@CartListActivity, lastCartItems,true)
                rvCartItemsList.adapter = cartListAdapter


            }
            var subTotal: Double = 0.0


            for (item in lastCartItems) {



                val availableQuantity = item.stock_quantity.toInt()

                if (availableQuantity > 0) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()

                    subTotal += (price * quantity)
                }

            }

          binding.tvSubTotal.text = "$$subTotal"

           binding.tvShippingCharge.text = "$10.0" //change the cargo fee logic here.

            if (subTotal > 0) {
                binding.llCheckout.visibility = View.VISIBLE

                val total = subTotal + 10
               binding.tvTotalAmount.text = "$$total"
            } else {
                binding.llCheckout.visibility = View.GONE
            }

        } else {
            binding.apply {
                rvCartItemsList.visibility = View.GONE
                llCheckout.visibility = View.GONE
                tvNoCartItemFound.visibility = View.VISIBLE
            }

        }


    }

    private fun getCartItemsList(){
        //showProgressBar(getString(R.string.please_wait))
        Database().getCartList(this@CartListActivity)

    }

    override fun onResume() {
        super.onResume()
        //getCartItemsList()
        getProductsFromFB()
    }



    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarCartListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }
        binding.toolbarCartListActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getProductsFromFB(){
        showProgressBar(getString(R.string.please_wait))
        Database().getAllProductsList(this)

    }


    fun successProductListFromFS(allProductList : ArrayList<Product>){
        hideProgressBar()
        lastProductsList = allProductList
        getCartItemsList()


    }

    fun itemRemovedSuccess(){
        hideProgressBar()
        Toast.makeText(this, R.string.msg_item_removed_successfully,Toast.LENGTH_LONG).show()
        getCartItemsList()
    }

    fun itemUpdateSuccess(){
        hideProgressBar()
        getCartItemsList()


    }






}