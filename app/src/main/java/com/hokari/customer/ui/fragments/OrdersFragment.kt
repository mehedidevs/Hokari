package com.hokari.customer.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hokari.customer.R
import com.hokari.customer.adapter.OrderAdapter
import com.hokari.customer.database.Database
import com.hokari.customer.model.Order
import com.hokari.customer.databinding.FragmentOrdersBinding
class OrdersFragment() : UiComponentsFragment() {
    private lateinit var binding: FragmentOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onResume() {
        super.onResume()
        getOrderList()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }
    fun showOrderList(orderList: ArrayList<Order>){
        hideProgressBar()
        if(orderList.size>0){
            binding.rvMyOrderItems.visibility = View.VISIBLE
            binding.tvNoOrdersFound.visibility = View.GONE
            binding.rvMyOrderItems.layoutManager = LinearLayoutManager(activity)
            binding.rvMyOrderItems.setHasFixedSize(true)
            val orderListAdapter = OrderAdapter(requireActivity(),orderList)
            binding.rvMyOrderItems.adapter = orderListAdapter
        }
        else{
            binding.rvMyOrderItems.visibility = View.GONE
            binding.tvNoOrdersFound.visibility = View.VISIBLE
        }
    }
    private fun getOrderList(){
        showProgressBar(getString(R.string.please_wait))
        Database().getOrderList(this)
    }
}