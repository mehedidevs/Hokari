package com.hokari.customer.ui.activities

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hokari.customer.R

import com.hokari.customer.adapter.AddressAdapter
import com.hokari.customer.database.Database
import com.hokari.customer.databinding.ActivityAddressBinding
import com.hokari.customer.model.Address
import com.hokari.customer.utils.Constants
import com.hokari.customer.utils.SwipeToDelete
import com.hokari.customer.utils.SwipeToEdit



class AddressActivity : UiComponentsActivity() {


    private var selectedAddress: Boolean = false
    lateinit var binding:ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAddressListActivity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }

        binding.toolbarAddressListActivity.setNavigationOnClickListener {onBackPressed()}

        binding.tvAddAddress.setOnClickListener {
            val intent = Intent(this@AddressActivity, EditAddAddressActivity::class.java)
            startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE)
        }

        if(intent.hasExtra("extra_select_address")){
            selectedAddress = intent.getBooleanExtra("extra_select_address",false)
        }

        if(selectedAddress){
            binding.tvTitle.text = getString(R.string.title_select_address)
        }

        getAddressList()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            getAddressList()
        }
    }

    fun addressListFromDBSuccess(addressList: ArrayList<Address>){
        hideProgressBar()

        if(addressList.size > 0){

            binding.apply {
                tvNoAddressFound.visibility = View.GONE
                rvAddressList.visibility = View.VISIBLE

                rvAddressList.layoutManager = LinearLayoutManager(this@AddressActivity)
                rvAddressList.setHasFixedSize(true)
                val addressAdapter = AddressAdapter(applicationContext,addressList,selectedAddress)
                rvAddressList.adapter = addressAdapter
            }


            if(!selectedAddress){
                val editSwipeHandler = object: SwipeToEdit(this){
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val adapter = binding.rvAddressList.adapter as AddressAdapter
                        adapter.notifyEditItem(this@AddressActivity,viewHolder.adapterPosition)
                    }
                }

                val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
                editItemTouchHelper.attachToRecyclerView(binding.rvAddressList)

                val deleteSwipeHandler = object : SwipeToDelete(this){
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        showProgressBar(getString(R.string.please_wait))

                        Database().deleteAddress(this@AddressActivity,addressList[viewHolder.adapterPosition].id)
                    }
                }

                val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
                deleteItemTouchHelper.attachToRecyclerView(binding.rvAddressList)
            }

        }else{
            binding.tvNoAddressFound.visibility = View.VISIBLE
        }

    }

    private fun getAddressList(){
        showProgressBar(getString(R.string.please_wait))
        Database().getAddresses(this)
    }

    fun deleteAddressSuccessful(){
        hideProgressBar()
        Toast.makeText(this, R.string.err_your_address_deleted_successfully,Toast.LENGTH_LONG).show()
        getAddressList()
    }










}