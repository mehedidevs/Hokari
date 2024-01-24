package com.hokari.customer.ui.activities


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.hokari.customer.R

import com.hokari.customer.database.Database
import com.hokari.customer.databinding.ActivityOrderDetailsBinding
import com.hokari.customer.databinding.ActivityProductDetailsBinding
import com.hokari.customer.model.Cart
import com.hokari.customer.model.Product
import com.hokari.customer.utils.Constants
import com.hokari.customer.utils.LoadGlide



class ProductDetailsActivity : UiComponentsActivity(),View.OnClickListener {

    private var myProductId: String = ""
    private var editPerm : Int = 0
    private lateinit var productModel: Product
    var productOwnerId : String = ""
    private lateinit var myProductDetails: Product
    lateinit var  binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()


        if(intent.hasExtra("extra_product_id")){
            myProductId = intent.getStringExtra("extra_product_id")!!
            editPerm = intent.getIntExtra("extra_edit_perm",0)

        }


        if(intent.hasExtra("extra_product_owner_id")){
            productOwnerId = intent.getStringExtra("extra_product_owner_id").toString()
        }

        if(Database().getUserID() == productOwnerId){


        }
        else{
            binding.btnAddToCart.visibility = View.VISIBLE
        }


        getProductDetails()

        binding.btnAddToCart.setOnClickListener(this)

        binding.btnGoToCart.setOnClickListener {
            startActivity(Intent(this@ProductDetailsActivity, CartListActivity::class.java))
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_product_menu,menu)
        if(editPerm == 0){
            //hide menu if user has no permission to edit product.(If its not user's product)
            val editMenu = menu!!.findItem(R.id.edit_product_menu_item)
            editMenu.isVisible = false
        }
        return super.onCreateOptionsMenu(menu)

    }



    fun getProductDetails(){
        showProgressBar(getString(R.string.please_wait))
        Database().getProductDetails(this@ProductDetailsActivity,myProductId)

    }



    fun productDetailsSuccess(product: Product){
        myProductDetails = product
        LoadGlide(this@ProductDetailsActivity).loadProductImage(product.image,binding.ivProductDetailImage)
        binding.apply {
            tvProductDetailsAvailableQuantity.text = product.stock_quantity
            tvProductDetailsDescription.text = product.description
            tvProductDetailsPrice.text = "$${product.price}"
            tvProductDetailsTitle.text = product.title
        }

        product.product_id = myProductId
        productModel = product

        if(product.stock_quantity.toInt() == 0){
            hideProgressBar()
            binding.apply {

                tvProductDetailsAvailableQuantity.text = getString(R.string.lbl_out_of_stock)
                tvProductDetailsAvailableQuantity.setTextColor(ContextCompat.getColor(this@ProductDetailsActivity,
                    R.color.colorSnackBarError
                ))
            }

        }
        else{
            if(Database().getUserID() == product.user_id){
                hideProgressBar()
            }
            else{
                Database().checkIfItemExistsInCart(this,product.product_id)
            }
        }


    }

    fun productExistsInCard(){
        hideProgressBar()

    }








    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this@ProductDetailsActivity, AddProductActivity::class.java)
        intent.putExtra("edit",1)
        intent.putExtra("productModel",productModel)
        when (item.itemId){
            //EDIT PRODUCT
            R.id.edit_product_menu_item -> startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }

    private fun addToCart(){
        val cartItem = Cart(
            Database().getUserID(),
            productOwnerId,
            myProductId,
            myProductDetails.title,
            myProductDetails.price,
            myProductDetails.image,
            Constants.DEFAULT_CART_QUANTITY
        )
        showProgressBar(getString(R.string.please_wait))
        Database().addItemToCart(this,cartItem)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_add_to_cart -> {
                    addToCart()
                }
            }

        }
    }

    fun addToCartSuccess(){
        hideProgressBar()
        Toast.makeText(this,getString(R.string.add_to_cart_success),Toast.LENGTH_LONG).show()

    }


    private fun setupActionBar() {
        lateinit var binding: ActivityProductDetailsBinding
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbarProductDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }

    }


}