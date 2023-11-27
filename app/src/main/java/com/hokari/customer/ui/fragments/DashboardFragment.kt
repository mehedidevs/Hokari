package com.hokari.customer.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager

import com.hokari.customer.adapter.DashboardListAdapter
import com.hokari.customer.database.Database
import com.hokari.customer.model.Product
import com.hokari.customer.ui.activities.CartListActivity
import com.hokari.customer.ui.activities.SettingsActivity



class DashboardFragment : UiComponentsFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onResume() {
        super.onResume()
        getDashboardList()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            R.id.cartMenuButton ->{
                startActivity(Intent(activity, CartListActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun successDashboardList(dashboardList : ArrayList<Product>){
        hideProgressBar()
        if(dashboardList.size>0){
            tv_no_dashboard_items_found.visibility = View.GONE
            rv_dashboard_items.visibility = View.VISIBLE
            rv_dashboard_items.layoutManager = GridLayoutManager(activity,2)
            rv_dashboard_items.setHasFixedSize(true)
            val adapterDashboard = DashboardListAdapter(requireActivity(),dashboardList)
            rv_dashboard_items.adapter = adapterDashboard
        }else{
            rv_dashboard_items.visibility = View.GONE
            tv_no_dashboard_items_found.visibility = View.VISIBLE
        }

    }

    private fun getDashboardList(){

        showProgressBar(getString(R.string.please_wait))
        Database().getItemsForDashboard(this@DashboardFragment)


    }



}