package com.hokari.customer.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hokari.customer.R
import com.hokari.customer.databinding.ActivityUiComponentsBinding
import com.hokari.customer.databinding.ProgressBarBinding


//This activity is for gathering common features that app uses in different activities.
//For now I created a few lines just for progressBar.

open class UiComponentsActivity : AppCompatActivity() {

    lateinit var myProgressDialog: Dialog
    private lateinit var binding: ActivityUiComponentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUiComponentsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    fun showProgressBar(text: String) {
        myProgressDialog = Dialog(this)
        val binding: ProgressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        myProgressDialog.setContentView(binding.root)
        binding.tvProgressText.setText(R.string.please_wait)
        myProgressDialog.setCancelable(false)
        myProgressDialog.setCanceledOnTouchOutside(false)
        myProgressDialog.show()
    }
    }

    fun hideProgressBar() {
        myProgressDialog.dismiss()
    }






}