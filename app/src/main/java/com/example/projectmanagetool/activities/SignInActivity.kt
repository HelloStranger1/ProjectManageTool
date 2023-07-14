package com.example.projectmanagetool.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import com.example.projectmanagetool.R

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setUpActionBar()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
    }

    private fun setUpActionBar(){
        val toolbarSignUp : Toolbar = findViewById(R.id.toolbar_sign_in_activity)
        setSupportActionBar(toolbarSignUp)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)

        }

        toolbarSignUp.setNavigationOnClickListener { onBackPressed() }
    }
}