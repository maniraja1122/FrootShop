package com.example.frootshop

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.frootshop.Repository.AuthHelper
import com.example.frootshop.databinding.ActivityFruitSelectedBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FruitSelected : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding:ActivityFruitSelectedBinding= ActivityFruitSelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        Log.v("mani",AuthHelper.SelectedFruit.name)
        CoroutineScope(Dispatchers.Main).launch {
            async {
                Glide.with(this@FruitSelected)
                    .load(AuthHelper.SelectedFruit.imgurl)
                    .into(object : SimpleTarget<Drawable?>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable?>?
                        ) {
                            binding.rootid.setBackground(resource)
                        }
                    })
            }}
      binding.fruitname1.setText(AuthHelper.SelectedFruit.name)
        binding.fruitrate.setText("$"+AuthHelper.SelectedFruit.price.toString())
        binding.quantity.setText(AuthHelper.SelectedFruit.quantity.toString())
        binding.increment.setOnClickListener(){
            if(AuthHelper.SelectedFruit.quantity<10) {
                AuthHelper.SelectedFruit.quantity+=1
                binding.quantity.setText(AuthHelper.SelectedFruit.quantity.toString())
            }
        }
        binding.decrement.setOnClickListener(){
            if(AuthHelper.SelectedFruit.quantity>0) {
                AuthHelper.SelectedFruit.quantity-=1
                binding.quantity.setText(AuthHelper.SelectedFruit.quantity.toString())
            }
        }
        binding.addcartbtn.setOnClickListener(){
            AuthHelper.AddFruit()
            Toast.makeText(applicationContext, "Item Added Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,Shop::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
        binding.backbtnfruit.setOnClickListener(){
            startActivity(Intent(this,Shop::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
    }
    override fun onBackPressed() {
    }
}