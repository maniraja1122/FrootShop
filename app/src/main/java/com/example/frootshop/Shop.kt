package com.example.frootshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.frootshop.Repository.AuthHelper
import com.example.frootshop.databinding.ActivityShopBinding

class Shop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding:ActivityShopBinding= ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.backbtnshop.setOnClickListener(){
            startActivity(Intent(this,HomeActivity::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
        binding.shoptitle.setText(AuthHelper.ShopsList[AuthHelper.SelectedShop].name)
        binding.shopitemcount.setText(AuthHelper.ShopsList[AuthHelper.SelectedShop].fruitlist.size.toString()+" Items")
        var adapter=FruitListAdapter(AuthHelper.ShopsList[AuthHelper.SelectedShop].fruitlist,this)
        binding.fruitlist.layoutManager=GridLayoutManager(this,2)
        binding.fruitlist.adapter=adapter
        binding.fruitlist.setOnClickListener(){
            Log.v("mani","Recyclr")
        }
    }
    override fun onBackPressed() {
    }
}