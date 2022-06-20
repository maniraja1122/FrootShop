package com.example.frootshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.frootshop.databinding.ActivitySelectorBinding

class Selector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding:ActivitySelectorBinding= ActivitySelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.createbtn.setOnClickListener(){
            startActivity(Intent(this,signuppage::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
        binding.loginbtn.setOnClickListener(){
            startActivity(Intent(this,Signinpage::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
    }
    override fun onBackPressed() {
    }
}