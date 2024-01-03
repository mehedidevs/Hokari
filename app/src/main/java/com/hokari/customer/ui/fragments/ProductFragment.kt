package com.hokari.customer.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.hokari.customer.R

import com.hokari.customer.adapter.ProductsListAdapter
import com.hokari.customer.database.Database
import com.hokari.customer.databinding.FragmentOrdersBinding
import com.hokari.customer.databinding.FragmentProductsBinding
import com.hokari.customer.model.Product
import com.hokari.customer.ui.activities.AddProductActivity
import com.hokari.customer.ui.activities.SoldProductsActivity
import io.grpc.Context


class ProductFragment : UiComponentsFragment() {
    lateinit var  binding: FragmentProductsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getProductList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_top_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.add_product -> {
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true
            }
            R.id.sold_products ->{
                startActivity(Intent(activity, SoldProductsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun successProductListFS(productList: ArrayList<Product>){
        hideProgressBar()
        if(productList.size>0){
            binding.apply {
                rvMyProductItems.visibility = View.VISIBLE
                tvNoProductsFound.visibility = View.GONE

                rvMyProductItems.layoutManager = LinearLayoutManager(activity)
                rvMyProductItems.setHasFixedSize(true)
                val adapterProducts = ProductsListAdapter(requireActivity(),productList, this@ProductFragment)
                rvMyProductItems.adapter = adapterProducts
            }

        }else{
            binding.rvMyProductItems.visibility = View.GONE
            binding.tvNoProductsFound.visibility = View.VISIBLE
        }



    }

    fun getProductList(){
        showProgressBar(resources.getString(R.string.please_wait))
        Database().getProductList(this)


    }



    fun deleteProduct(productId: String) {

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.delete_product_title))
        builder.setMessage(getString(R.string.are_you_sure_want_to_delete_product))
        builder.setIcon(R.drawable.ic_baseline_warning_24)
        builder.setPositiveButton(getString(R.string.yes)){ d, _ ->
            showProgressBar(getString(R.string.please_wait))
            Database().deleteProduct(this,productId)
            d.dismiss()
        }
        builder.setNegativeButton(getString(R.string.no)){ d, _ ->
            d.dismiss()

        }
        val alert : AlertDialog = builder.create()
        alert.setCancelable(false)
        alert.show()


    }


    fun productDeleteSuccess(){
        hideProgressBar()
        getProductList()

    }



}
