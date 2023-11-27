package com.hokari.customer.ui.activities


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import com.hokari.customer.adapter.SoldProductAdapter
import com.hokari.customer.database.Database
import com.hokari.customer.model.SoldProduct


class SoldProductsActivity : UiComponentsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sold_products)
        setupActionBar()

        getSoldProductList()

    }



    private fun getSoldProductList(){
        showProgressBar(getString(R.string.please_wait))
        Database().getSoldProductsList(this)
    }

    fun successSoldProductList(soldProductList: ArrayList<SoldProduct>){
        hideProgressBar()
        if(soldProductList.size>0){
            rv_sold_product_items.visibility = View.VISIBLE
            tv_no_sold_products_found.visibility = View.GONE

            rv_sold_product_items.layoutManager = LinearLayoutManager(this)
            rv_sold_product_items.setHasFixedSize(true)
            val soldProductsAdapter = SoldProductAdapter(this,soldProductList)
            rv_sold_product_items.adapter = soldProductsAdapter
        }
        else{
            rv_sold_product_items.visibility = View.GONE
            tv_no_sold_products_found.visibility = View.VISIBLE
        }

    }



    private fun setupActionBar() {

        setSupportActionBar(toolbar_sold_products_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }
        toolbar_sold_products_activity.setNavigationOnClickListener { onBackPressed() }
    }
}