package com.hokari.customer.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hokari.customer.R
import com.hokari.customer.databinding.ProgressBarBinding


open class UiComponentsFragment : Fragment() {

    private lateinit var myProgressDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ui_components, container, false)
    }

    fun showProgressBar(text: String) {
        lateinit var binding: ProgressBarBinding
        binding = ProgressBarBinding.inflate(layoutInflater)
        myProgressDialog = Dialog(requireActivity())
        myProgressDialog.setContentView(R.layout.dialog_progress)
        binding.tvProgressText.text = text
        myProgressDialog.setCancelable(false)
        myProgressDialog.setCanceledOnTouchOutside(false)
        myProgressDialog.show()
    }

    fun hideProgressBar() {
        myProgressDialog.dismiss()
    }


}