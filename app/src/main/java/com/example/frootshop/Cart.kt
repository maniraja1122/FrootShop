package com.example.frootshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frootshop.MVVM.MainViewModel
import com.example.frootshop.Repository.AuthHelper
import com.example.frootshop.databinding.ActivityCartBinding
import kotlinx.coroutines.*

class Cart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding:ActivityCartBinding= ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.backbtncart.setOnClickListener(){
            startActivity(Intent(this,HomeActivity::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
        var adapter:CartItemAdapter= CartItemAdapter(AuthHelper.CurrentUser.cartlist!!)
        binding.cartlist.layoutManager=LinearLayoutManager(this)
        binding.cartlist.adapter=adapter
        var mymodel=ViewModelProvider(this).get(MainViewModel::class.java)
        mymodel.FruitList.observe(this, Observer {
            var adapter1:CartItemAdapter= CartItemAdapter(AuthHelper.CurrentUser.cartlist!!)
            binding.cartlist.adapter=adapter1
        })
        binding.checkbtn.setOnClickListener(){
            AuthHelper.EmptyCart()
            Toast.makeText(applicationContext, "Your Items will be delivered shortly....", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
        CoroutineScope(Dispatchers.Main).launch{
            async {
                while (true){
                    mymodel.UpdateList()
                    delay(200)
                }
            }
        }
    }
    override fun onBackPressed() {
    }
}