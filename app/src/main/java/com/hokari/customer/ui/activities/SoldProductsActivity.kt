package com.hokari.customer.ui.activities


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hokari.customer.R
import com.hokari.customer.adapter.SoldProductAdapter
import com.hokari.customer.database.Database
import com.hokari.customer.databinding.ActivitySoldProductsBinding
import com.hokari.customer.model.SoldProduct


class SoldProductsActivity : UiComponentsActivity() {

    private lateinit var binding: ActivitySoldProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySoldProductsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
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
            binding.rvSoldProductItems.visibility = View.VISIBLE
            binding.tvNoSoldProductsFound.visibility = View.GONE

            binding.rvSoldProductItems.layoutManager = LinearLayoutManager(this)
            binding.rvSoldProductItems.setHasFixedSize(true)
            val soldProductsAdapter = SoldProductAdapter(this,soldProductList)
            binding.rvSoldProductItems.adapter = soldProductsAdapter
        }
        else{
            binding.rvSoldProductItems.visibility = View.GONE
            binding.tvNoSoldProductsFound.visibility = View.VISIBLE
        }

    }



    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarSoldProductsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }
        binding.toolbarSoldProductsActivity.setNavigationOnClickListener { onBackPressed() }
    }
}