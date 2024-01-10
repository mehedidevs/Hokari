package com.hokari.customer.ui.fragments
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hokari.customer.R
import com.hokari.customer.databinding.FragmentUiComponentsBinding
import com.hokari.customer.databinding.ProgressBarBinding
import org.checkerframework.common.returnsreceiver.qual.This

open class UiComponentsFragment : Fragment() {
    private lateinit var myProgressDialog : Dialog
    private lateinit var binding: FragmentUiComponentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUiComponentsBinding.inflate(inflater, container, false)
        return binding.root
    }
    fun showProgressBar(string: String) {
        myProgressDialog = Dialog(this)
//                TODO: Fix This
        val binding: ProgressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        myProgressDialog.setContentView(binding.root)
        binding.tvProgressText.setText(R.string.please_wait)
        myProgressDialog.setCancelable(false)
        myProgressDialog.setCanceledOnTouchOutside(false)
        myProgressDialog.show()
        }




    fun hideProgressBar() {
        myProgressDialog.dismiss()
    }
}

