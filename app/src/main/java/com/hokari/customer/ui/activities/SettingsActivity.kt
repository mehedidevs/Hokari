package com.hokari.customer.ui.activities

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.hokari.customer.database.Database
import com.hokari.customer.databinding.ActivityProductDetailsBinding
import com.hokari.customer.databinding.ActivitySettingsBinding
import com.hokari.customer.model.User
import com.hokari.customer.utils.Constants
import com.hokari.customer.utils.LoadGlide
import com.hokari.customer.R

class SettingsActivity : UiComponentsActivity() {

    private lateinit var myUserDetails: User
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarSettingsActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }

        binding.toolbarSettingsActivity.setNavigationOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogout.setOnClickListener {
            showProgressBar("Logging Out...")
            Firebase.auth.signOut()
            hideProgressBar()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.tvEdit.setOnClickListener {

            val intent = Intent(this@SettingsActivity, UserProfileActivity::class.java)
            intent.putExtra(
                "fromSettings",
                1
            ) //inform userprofile that we are coming from settings.
            intent.putExtra(Constants.EXTRA_USER_DETAILS, myUserDetails)
            startActivity(intent)
        }

        binding.llAddress.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            startActivity(intent)
        }


    }


    private fun getUserDetails() {
        showProgressBar(resources.getString(R.string.please_wait))
        Database().getCurrentUserDetails(this)


    }

    fun userDetailsSuccess(user: User) {
        myUserDetails = user
        hideProgressBar()
        LoadGlide(this@SettingsActivity).loadUserPicture(user.image, binding.ivUserPhoto)

        binding.apply {
            tvName.text = "${user.firstName} ${user.lastName}"
            tvGender.text = user.gender
            tvEmail.text = user.email
            tvMobileNumber.text = user.mobile.toString()
        }


    }


    override fun onResume() {
        super.onResume()
        getUserDetails()
    }
}