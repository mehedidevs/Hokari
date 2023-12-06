package com.hokari.customer.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hokari.customer.R
import com.hokari.customer.databinding.ActivityUiComponentsBinding


//This activity is for gathering common features that app uses in different activities.
//For now I created a few lines just for progressBar.

open class UiComponentsActivity : AppCompatActivity() {

    private lateinit var myProgressDialog: Dialog
    private lateinit var binding: ActivityUiComponentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUiComponentsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    fun showProgressBar(text: String) {
        myProgressDialog = Dialog(this)
        myProgressDialog.setContentView(R.layout.dialog_progress)
//        myProgressDialog.tv_progress_text.text = text
        myProgressDialog.setCancelable(false)
        myProgressDialog.setCanceledOnTouchOutside(false)
        myProgressDialog.show()
    }

    fun hideProgressBar() {
        myProgressDialog.dismiss()
    }






}