package com.example.frootshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frootshop.Classes.User
import com.example.frootshop.Repository.AuthHelper
import com.example.frootshop.databinding.ActivitySignuppageBinding

class signuppage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding:ActivitySignuppageBinding= ActivitySignuppageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.createbtn.setOnClickListener(){
            var username= binding.nametxt.text.toString()
            var myemail=binding.emailtxt.text.toString()
            var mypassword=binding.passtxt.text.toString().trim()
            if(username!="" && AuthHelper.isValidEmailId(myemail) && mypassword.length>=6){
                AuthHelper.auth.createUserWithEmailAndPassword(myemail,mypassword).addOnCompleteListener(){
                    if(it.isSuccessful) {
                        AuthHelper.CreateUser()
                        AuthHelper.SetUser()
                        AuthHelper.SetShopList()
                        Toast.makeText(applicationContext, "User Created Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,HomeActivity::class.java))
                        overridePendingTransition(R.anim.slidein, R.anim.slideout)
                    }
                    else{
                        Toast.makeText(applicationContext, it.exception!!.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                if(username==""){
                    binding.nametxt.setError("Please Enter A Name First")
                }
                if(!AuthHelper.isValidEmailId(myemail)){
                    binding.emailtxt.setError("Please enter a valid email")
                }
                if(mypassword.length<6){
                    binding.passtxt.setError("Password should be atleast 6 characters long")
                }
            }
        }
        binding.backbtnsel.setOnClickListener(){
            startActivity(Intent(this,Selector::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
    }
    override fun onBackPressed() {
    }
}