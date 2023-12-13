package com.hokari.customer.ui.activities


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hokari.customer.R

import com.hokari.customer.adapter.SoldProductAdapter
import com.hokari.customer.database.Database
import com.hokari.customer.databinding.ActivitySettingsBinding
import com.hokari.customer.databinding.ActivitySoldProductsBinding
import com.hokari.customer.model.SoldProduct


class SoldProductsActivity : UiComponentsActivity() {

    lateinit var binding: ActivitySoldProductsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoldProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            binding.apply {
                rvSoldProductItems.visibility = View.VISIBLE
                tvNoSoldProductsFound.visibility = View.GONE

                rvSoldProductItems.layoutManager = LinearLayoutManager(this@SoldProductsActivity)
                rvSoldProductItems.setHasFixedSize(true)
                val soldProductsAdapter = SoldProductAdapter(this@SoldProductsActivity,soldProductList)
                rvSoldProductItems.adapter = soldProductsAdapter
            }

        }
        else{
            binding.rvSoldProductItems.visibility = View.GONE
           binding.tvNoSoldProductsFound.visibility = View.VISIBLE
        }

    }



    private fun setupActionBar() {
        lateinit var binding: ActivitySoldProductsBinding
        binding =ActivitySoldProductsBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbarSoldProductsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }
        binding.toolbarSoldProductsActivity.setNavigationOnClickListener { onBackPressed() }
    }
}