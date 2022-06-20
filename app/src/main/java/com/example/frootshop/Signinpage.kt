package com.example.frootshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frootshop.Repository.AuthHelper
import com.example.frootshop.databinding.ActivitySigninpageBinding

class Signinpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding:ActivitySigninpageBinding= ActivitySigninpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.signinbtn.setOnClickListener(){
            var myemail=binding.emailtxt1.text.toString()
            var mypassword=binding.passtxt1.text.toString().trim()
            if(AuthHelper.isValidEmailId(myemail) && mypassword.length>=6){
                AuthHelper.auth.signInWithEmailAndPassword(myemail,mypassword).addOnCompleteListener(){
                    if(it.isSuccessful) {
                        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                        AuthHelper.SetUser()
                        AuthHelper.SetShopList()
                        startActivity(Intent(this,HomeActivity::class.java))
                        overridePendingTransition(R.anim.slidein, R.anim.slideout)
                    }
                    else{
                        Toast.makeText(applicationContext, it.exception!!.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                if(!AuthHelper.isValidEmailId(myemail)){
                    binding.emailtxt1.setError("Please enter a valid email")
                }
                if(mypassword.length<6){
                    binding.passtxt1.setError("Password should be atleast 6 characters long")
                }
            }
        }
        binding.backbtnsel1.setOnClickListener(){
            startActivity(Intent(this,Selector::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
    }
    override fun onBackPressed() {
    }
}