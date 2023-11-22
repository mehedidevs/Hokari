package com.hokari.customer

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hokari.customer.databinding.ActivityLoginBinding
import com.hokari.customer.databinding.ProgressBarBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    lateinit var mAuth: FirebaseAuth
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        dialog = Dialog(this@LoginActivity)


        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.tvRegister.setOnClickListener {

            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))


        }


    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email != "" && password != "") {
            showProgressDialog()
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()


            }.addOnFailureListener {
                Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()

            }


        }


    }

    private fun hideProgressDialog() {
        dialog.dismiss()

    }

    private fun showProgressDialog() {

        val progBinding = ProgressBarBinding.inflate(layoutInflater)

        dialog.setContentView(progBinding.root)
        progBinding.apply {
            tvProgressText.text = "Please wait....."
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()


    }
}