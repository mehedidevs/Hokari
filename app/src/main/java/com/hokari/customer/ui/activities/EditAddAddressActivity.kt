package com.hokari.customer.ui.activities


import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.hokari.customer.R

import com.hokari.customer.database.Database
import com.hokari.customer.databinding.ActivityCheckoutBinding
import com.hokari.customer.databinding.ActivityEditAddAddressBinding
import com.hokari.customer.model.Address


class EditAddAddressActivity : UiComponentsActivity() {

    private var lastAddressDetails: Address? = null
    lateinit var binding: ActivityEditAddAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAddEditAddressActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }

        binding.toolbarAddEditAddressActivity.setNavigationOnClickListener { onBackPressed() }

        if (intent.hasExtra("extra_address_details")) {
            lastAddressDetails = intent.getParcelableExtra("extra_address_details")
        }
        if (lastAddressDetails != null) {
            if (lastAddressDetails!!.id.isNotEmpty()) {
                binding.apply {
                    tvTitle.text = resources.getString(R.string.title_edit_address)
                    btnSubmitAddress.text = resources.getString(R.string.btn_lbl_update)

                    etFullName.setText(lastAddressDetails?.fullName)
                    etPhoneNumber.setText(lastAddressDetails?.mobileNumber)
                    etAddress.setText(lastAddressDetails?.address)
                    etZipCode.setText(lastAddressDetails?.zipCode)
                    etAdditionalNote.setText(lastAddressDetails?.additionalNote)
                }


                when (lastAddressDetails?.type) {
                    "Home" -> {
                        binding.rbHome.isChecked = true
                    }

                    "Office" -> {
                        binding.rbOffice.isChecked = true
                    }

                    else -> {

                        binding.apply {
                            rbOther.isChecked = true
                            tilOtherDetails.visibility = View.VISIBLE
                            etOtherDetails.setText(lastAddressDetails?.otherDetails)
                        }

                    }
                }
            }
        }

        binding.btnSubmitAddress.setOnClickListener {
            saveAddressToDatabase()
        }

        binding.rgType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_other) {
                binding.tilOtherDetails.visibility = View.VISIBLE
            } else {
                binding.tilOtherDetails.visibility = View.GONE
            }
        }

    }


    private fun saveAddressToDatabase() {


        binding.apply {
            val fullName: String = etFullName.text.toString().trim()
            val phoneNumber: String = etPhoneNumber.text.toString().trim()
            val address: String = etAddress.text.toString().trim()
            val zipCode: String = etZipCode.text.toString().trim()
            val additionalNote: String = etAdditionalNote.text.toString().trim()
            val otherDetails: String = etOtherDetails.text.toString().trim()


            val addressType: String = when {
                binding.rbHome.isChecked -> {
                    "Home"
                }

                binding.rbOffice.isChecked -> {
                    "Office"
                }

                else -> {
                    "Other"
                }
            }

            val addressModel = Address(
                userId = Database().getUserID(),
                fullName = fullName,
                mobileNumber = phoneNumber,
                address = address,
                zipCode = zipCode,
                additionalNote = additionalNote,
                type = addressType,
                otherDetails = otherDetails
            )


            if (validateData()) {
                showProgressBar(resources.getString(R.string.please_wait))

                if (lastAddressDetails != null && lastAddressDetails!!.id.isNotEmpty()) {
                    Database().updateAddress(
                        this@EditAddAddressActivity,
                        addressModel,
                        lastAddressDetails!!.id
                    )
                } else {
                    Database().addAddress(this@EditAddAddressActivity, addressModel)
                }

            }


        }


    }


    fun editAddAddressToDBSuccess() {
        hideProgressBar()
        val successMessage: String =
            if (lastAddressDetails != null && lastAddressDetails!!.id.isNotEmpty()) {
                resources.getString(R.string.msg_your_address_updated_successfully)
            } else {
                resources.getString(R.string.err_your_address_added_successfully)
            }
        Toast.makeText(this@EditAddAddressActivity, successMessage, Toast.LENGTH_LONG).show()
        setResult(RESULT_OK)
        finish()


    }


    private fun validateData(): Boolean {
        return when {

            TextUtils.isEmpty(binding.etFullName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, R.string.err_msg_please_enter_full_name, Toast.LENGTH_LONG)
                    .show()
                false
            }

            TextUtils.isEmpty(binding.etPhoneNumber.text.toString().trim { it <= ' ' }) -> {

                Toast.makeText(this, R.string.err_msg_please_enter_phone_number, Toast.LENGTH_LONG)
                    .show()
                false
            }

            TextUtils.isEmpty(binding.etAddress.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, R.string.err_msg_please_enter_address, Toast.LENGTH_LONG)
                    .show()
                false
            }

            TextUtils.isEmpty(binding.etZipCode.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, R.string.err_msg_please_enter_zip_code, Toast.LENGTH_LONG)
                    .show()
                false
            }

            binding.rbOther.isChecked && TextUtils.isEmpty(
                binding.etZipCode.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, R.string.err_msg_please_enter_zip_code, Toast.LENGTH_LONG)
                    .show()
                false
            }

            else -> {
                true
            }
        }
    }


}