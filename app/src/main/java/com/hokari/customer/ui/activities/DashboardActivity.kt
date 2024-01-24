package com.hokari.customer.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hokari.customer.R
import com.hokari.customer.databinding.ActivityDashboardBinding
import com.hokari.customer.ui.fragments.DashboardFragment
import com.hokari.customer.ui.fragments.OrdersFragment
import com.hokari.customer.ui.fragments.ProductFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this@DashboardActivity,
                R.drawable.app_gradient_color_background
            )
        )
        binding.btnAddProduct.setOnClickListener {

            startActivity(Intent(this, AddProductActivity::class.java))

        }



        replaceFragment(DashboardFragment())
        supportActionBar?.title = getString(R.string.explore_buy_title)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> replaceFragment(DashboardFragment())
                R.id.orders -> replaceFragment(OrdersFragment())
                R.id.products -> replaceFragment(ProductFragment())

                else -> {

                }
            }

            when (it.itemId) {
                R.id.dashboard -> supportActionBar?.title = getString(R.string.explore_buy_title)
                R.id.orders -> supportActionBar?.title = getString(R.string.orders_title)
                R.id.products -> supportActionBar?.title = getString(R.string.sell_product_title)
            }


            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()

    }


}