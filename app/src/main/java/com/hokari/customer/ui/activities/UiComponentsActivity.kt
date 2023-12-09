package com.hokari.customer.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hokari.customer.R
import com.hokari.customer.databinding.ActivitySoldProductsBinding
import com.hokari.customer.databinding.ProgressBarBinding


//This activity is for gathering common features that app uses in different activities.
//For now I created a few lines just for progressBar.

open class UiComponentsActivity : AppCompatActivity() {

    private lateinit var myProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_components)
    }


    fun showProgressBar(text: String) {
        lateinit var binding: ProgressBarBinding
        binding = ProgressBarBinding.inflate(layoutInflater)
        myProgressDialog = Dialog(this)
        myProgressDialog.setContentView(binding.root)
        binding.tvProgressText.text = text
        myProgressDialog.setCancelable(false)
        myProgressDialog.setCanceledOnTouchOutside(false)
        myProgressDialog.show()
    }

    fun hideProgressBar() {
        myProgressDialog.dismiss()
    }


}