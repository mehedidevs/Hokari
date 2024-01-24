package com.hokari.customer.ui.activities

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hokari.customer.R
import com.hokari.customer.database.Database
import com.hokari.customer.databinding.ActivityForgotPasswordBinding
import com.hokari.customer.databinding.ActivityLoginBinding
import com.hokari.customer.databinding.ProgressBarBinding
import com.hokari.customer.model.User
import com.hokari.customer.utils.AccountType
import com.hokari.customer.utils.Constants


class LoginActivity : AppCompatActivity() {

    lateinit var myProgressDialog: Dialog
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.tvRegister.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }



        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser != null) {


            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)

        }


        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }


    }


    fun loginUser() {

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email != "" && password != "") {
            showProgressBar()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    Database().getCurrentUserDetails(this@LoginActivity)
                } else {
                    hideProgressBar()
                    Toast.makeText(
                        this,
                        getString(R.string.login_failed_try_again),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            hideProgressBar()
            Toast.makeText(this, getString(R.string.dont_leave_entries_blank), Toast.LENGTH_LONG)
                .show()
        }

    }


    private fun showProgressBar() {

        lateinit var binding: ProgressBarBinding
        binding = ProgressBarBinding.inflate(layoutInflater)
        myProgressDialog = Dialog(this)
        myProgressDialog.setContentView(R.layout.progress_bar)
        binding.tvProgressText.setText(R.string.please_wait)
        myProgressDialog.setCancelable(false)
        myProgressDialog.setCanceledOnTouchOutside(false)
        myProgressDialog.show()
    }

    private fun hideProgressBar() {
        myProgressDialog.dismiss()
    }

    fun userLoggedInSuccess(user: User) {
        hideProgressBar()
        val sharedPreferences = getSharedPreferences(Constants.SHOP_PREFERENCES, MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.CURRENT_NAME, "")
        val welcomeString: String = getString(R.string.welcome_user) + "$username"
        Toast.makeText(this, welcomeString, Toast.LENGTH_LONG).show()
        if (user.profileCompleted == 0) {
            if (user.type == AccountType.customer) {

                val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
                intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
                startActivity(intent)
            } else if (user.type == AccountType.seller) {

                startActivity(Intent(this@LoginActivity, AddProductActivity::class.java))
            }


        } else {
            if (user.type == AccountType.customer) {

                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
            } else if (user.type == AccountType.seller) {

                startActivity(Intent(this@LoginActivity, AddProductActivity::class.java))
            }

        }
        finish()
    }


}