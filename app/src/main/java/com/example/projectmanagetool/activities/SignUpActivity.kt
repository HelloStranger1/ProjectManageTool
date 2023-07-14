package com.example.projectmanagetool.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import com.example.projectmanagetool.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : BaseActivity() {

    private var etName : EditText? = null
    private var etEmail : EditText? = null
    private var etPass : EditText? = null

    private var btnSignUp : AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setUpActionBar()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etPass = findViewById(R.id.et_password)
        btnSignUp = findViewById(R.id.btn_sign_up)

        btnSignUp?.setOnClickListener{

            registerUser()
        }


    }
    private fun setUpActionBar(){
        val toolbarSignUp : Toolbar = findViewById(R.id.toolbar_sign_up_activity)
        setSupportActionBar(toolbarSignUp)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)

        }

        toolbarSignUp.setNavigationOnClickListener { onBackPressed() }
    }

    private fun registerUser(){
        val name: String = etName!!.text.toString().trim{ it <= ' ' }
        val email: String = etEmail!!.text.toString().trim{ it <= ' ' }
        val password: String = etPass!!.text.toString().trim{it <= ' '}

        Log.e("sign up", "button clicked`")
        if(validateForm(name, email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        Toast.makeText(
                            this,
                            "$name you have successfully registered the email address $registeredEmail",
                            Toast.LENGTH_LONG
                        ).show()
                        FirebaseAuth.getInstance().signOut()
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            task.exception!!.message, Toast.LENGTH_SHORT
                        ).show()
                    }

                }

        }
    }

    private fun validateForm(name : String, email : String, password : String) : Boolean{
        Log.e("here", "here")
        return when{
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter an email address")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                Log.e("pass", "Missing passowrd")
                false
            }else -> {
                true
            }

        }
    }
}